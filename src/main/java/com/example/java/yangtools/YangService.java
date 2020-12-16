package com.example.java.yangtools;

import com.example.java.atomix.AtomixManager;
import com.example.java.config.Filter;
import com.example.java.event.DynamicConfigEvent;
import com.example.java.exception.FailedException;
import io.atomix.core.Atomix;
import io.atomix.core.map.DistributedMap;
import io.atomix.core.map.MapEventListener;
import io.atomix.core.tree.AsyncAtomicDocumentTree;
import io.atomix.core.tree.DocumentTreeEventListener;
import io.atomix.utils.event.Event;
import io.atomix.protocols.backup.MultiPrimaryProtocol;
import io.atomix.utils.time.Versioned;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.internal.guava.Lists;
import org.onosproject.store.service.DocumentTreeEvent;
import org.onosproject.store.service.DocumentTreeListener;
import org.onosproject.store.service.IllegalDocumentModificationException;
import org.onosproject.store.service.NoSuchDocumentPathException;
import org.onosproject.yang.model.*;
import org.onosproject.yang.runtime.YangRuntimeService;
import org.onosproject.yang.runtime.impl.DefaultYangRuntimeHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.example.java.event.DynamicConfigEvent.Type.*;

@Service
@Slf4j
public class YangService {
    /**
     * SchemaId namespace for DCS defined nodes.
     */
    public static final String DCS_NAMESPACE = "org.onosproject.dcs";

    // FIXME '/' is problematic name from RFC 7950/7951 point of view
    /**
     * SchemaId name for root node.
     */
    public static final String ROOT_NAME = "/";
    /**
     * SchemaId name for devices node.
     */
    public static final String DEVICES_NAME = "devices";
    public static final SchemaId DEVICES_SCHEMA = new SchemaId(DEVICES_NAME, DCS_NAMESPACE);
    /**
     * SchemaId name for device node.
     */
    public static final String DEVICE_NAME = "device";
    public static final SchemaId DEVICE_SCHEMA = new SchemaId(DEVICE_NAME, DCS_NAMESPACE);

    Atomix atomix;

    AsyncAtomicDocumentTree keystore;

    DistributedMap map;
    YangRuntimeService runtimeService;


    /**
     * @param atomixManager
     */
    public YangService(AtomixManager atomixManager) {
        atomix = atomixManager.getAtomix();

        keystore = atomix.<String>atomicDocumentTreeBuilder("my-tree")
                .withProtocol(MultiPrimaryProtocol.builder()
                        .build())
                .build().async();
//        new}
        io.atomix.core.tree.DocumentTreeEventListener atomixListener = event -> {
            DocumentPath path = toOnosPath((io.atomix.core.tree.DocumentPath) event.subject());
            ResourceId spath = ResourceIdParser.getResId(path.pathElements());
            System.out.println(isUnderDeviceRootNode(spath));
            if (isUnderDeviceRootNode(spath)) {
                DataNode snode = readNode(spath, null).join();
                if (snode.type().equals(DataNode.Type.SINGLE_INSTANCE_LEAF_VALUE_NODE)) {
                    System.out.println("读取叶子节点的配置：{}" + snode );
                }
            }
        };
        keystore.addListener(atomixListener);

        map = atomix.<String, String>mapBuilder("my-map")
                .build();
        map.addListener(new MapEventListener() {
            @Override
            public void event(Event event) {
                System.out.println("map update" + event.subject());
            }
        });
    }


    private DocumentPath toOnosPath(io.atomix.core.tree.DocumentPath path) {
        List<String> pathElements = Lists.newArrayList(path.pathElements());
        pathElements.set(0, DocumentPath.ROOT.pathElements().get(0));
        return DocumentPath.from(pathElements);
    }

    private Object toVersioned(io.atomix.utils.time.Versioned versioned) {
        return versioned != null
                ? new Versioned<>(versioned.value(), versioned.version(), versioned.creationTime())
                : null;
    }


    public static boolean isUnderDeviceRootNode(ResourceId path) {
        log.info("path:{}", path);
        return DeviceResourceIds.isUnderDeviceRootNode(path);
    }

    private void traverseInner(String path, InnerNode node) {
        addKey(path, node.type());
        Map<NodeKey, DataNode> entries = node.childNodes();
        if (entries.size() == 0) {
            return;
        }
        // FIXME ignoring results
        entries.forEach((k, v) -> {
            String tempPath;
            tempPath = ResourceIdParser.appendNodeKey(path, v.key());
            if (v.type() == DataNode.Type.SINGLE_INSTANCE_LEAF_VALUE_NODE) {
                addLeaf(tempPath, (LeafNode) v);
            } else if (v.type() == DataNode.Type.MULTI_INSTANCE_LEAF_VALUE_NODE) {
                tempPath = ResourceIdParser.appendLeafList(tempPath, (LeafListKey) v.key());
                addLeaf(tempPath, (LeafNode) v);

            } else if (v.type() == DataNode.Type.SINGLE_INSTANCE_NODE) {
                traverseInner(tempPath, (InnerNode) v);
            } else if (v.type() == DataNode.Type.MULTI_INSTANCE_NODE) {
                tempPath = ResourceIdParser.appendKeyList(tempPath, (ListKey) v.key());
                traverseInner(tempPath, (InnerNode) v);
            } else {
                throw new FailedException("Invalid node type");
            }
        });
    }

    private Boolean addKey(String path, DataNode.Type type) {
        DocumentPath dpath = DocumentPath.from(path);
        // FIXME Not atomic, should probably use create or replace
        if (completeVersioned(keystore.get(toAtomixPath(dpath))) != null) {
            completeVersioned(keystore.set(toAtomixPath(dpath), type));
            return true;
        }
        keystore.create(toAtomixPath(dpath), type);
        return true;
    }

    private Boolean addLeaf(String path, LeafNode node) {
        map.put(path, node);
        return addKey(path, node.type());
    }

    private <T> T completeVersioned(CompletableFuture<Versioned<T>> future) {
        return Optional.ofNullable(complete(future))
                .map(Versioned::value)
                .orElse(null);
    }

    private <T> T complete(CompletableFuture<T> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new FailedException(e.getCause().getMessage());
        } catch (ExecutionException e) {
            if (e.getCause() instanceof IllegalDocumentModificationException) {
                throw new FailedException("Node or parent does not exist or is root or is not a Leaf Node",
                        e.getCause());
            } else if (e.getCause() instanceof NoSuchDocumentPathException) {
                throw new FailedException("ResourceId does not exist", e.getCause());
            } else {
                throw new FailedException("Datastore operation failed", e.getCause());
            }
        }
    }

    private io.atomix.core.tree.DocumentPath toAtomixPath(DocumentPath path) {
        List<String> pathElements = Lists.newArrayList(path.pathElements());
        // We need to remove the root element here since the Atomix factory method assumes no root is present.
        pathElements.remove(0);
        return io.atomix.core.tree.DocumentPath.from(pathElements);
    }

    public CompletableFuture<Boolean> addNode(ResourceId parent, DataNode node) {
        String spath = ResourceIdParser.parseResId(parent);
        if (spath == null) {
            throw new FailedException("Invalid ResourceId, cannot create Node");
        }
        if (spath.equals(ResourceIdParser.ROOT)) {
            //If not present, adding static ROOT node after immutable documentTree root.
            if (complete(keystore.get(toAtomixPath(DocumentPath.from(spath)))) == null) {
                addLeaf(spath, LeafNode.builder(DeviceResourceIds.ROOT_NAME, DCS_NAMESPACE)
                        .type(DataNode.Type.SINGLE_INSTANCE_NODE).build());
            }
            ResourceId abs = ResourceIds.resourceId(parent, node);
            parseNode(ResourceIdParser.parseResId(abs), node);
            return CompletableFuture.completedFuture(true);
        } else if (complete(keystore.get(toAtomixPath(DocumentPath.from(spath)))) == null) {
            throw new FailedException("Node or parent does not exist for " + spath);
        }
        ResourceId abs = ResourceIds.resourceId(parent, node);
        //spath = ResourceIdParser.appendNodeKey(spath, node.key());
        parseNode(ResourceIdParser.parseResId(abs), node);
        return CompletableFuture.completedFuture(true);
    }

    public CompletableFuture<DataNode> readNode(ResourceId path, Filter filter) {
        CompletableFuture<DataNode> eventFuture = CompletableFuture.completedFuture(null);
        String spath = ResourceIdParser.parseResId(path);
        DocumentPath dpath = DocumentPath.from(spath);
        DataNode.Type type = null;
        CompletableFuture<Versioned<DataNode.Type>> ret = keystore.get(toAtomixPath(dpath));
        type = completeVersioned(ret);
        if (type == null) {
            throw new FailedException("Requested node or some of the parents " +
                    "are not present in the requested path: " +
                    spath);
        }
        DataNode retVal = null;
        if (type == DataNode.Type.SINGLE_INSTANCE_LEAF_VALUE_NODE) {
            retVal = readLeaf(spath);
        } else if (type == DataNode.Type.MULTI_INSTANCE_LEAF_VALUE_NODE) {
            retVal = readLeaf(spath);
        } else if (type == DataNode.Type.SINGLE_INSTANCE_NODE) {
            NodeKey key = ResourceIdParser.getInstanceKey(path);
            if (key == null) {
                throw new FailedException("Key type did not match node type");
            }
            DataNode.Builder superBldr = InnerNode
                    .builder(key.schemaId().name(), key.schemaId().namespace())
                    .type(type);
            readInner(superBldr, spath);
            retVal = superBldr.build();
        } else if (type == DataNode.Type.MULTI_INSTANCE_NODE) {
            NodeKey key = ResourceIdParser.getMultiInstanceKey(path);
            if (key == null) {
                throw new FailedException("Key type did not match node type");
            }
            DataNode.Builder superBldr = InnerNode
                    .builder(key.schemaId().name(), key.schemaId().namespace())
                    .type(type);
            for (KeyLeaf keyLeaf : ((ListKey) key).keyLeafs()) {
                //String tempPath = ResourceIdParser.appendKeyLeaf(spath, keyLeaf);
                //LeafNode lfnd = readLeaf(tempPath);
                superBldr.addKeyLeaf(keyLeaf.leafSchema().name(),
                        keyLeaf.leafSchema().namespace(), String.valueOf(keyLeaf.leafValue()));
            }
            readInner(superBldr, spath);
            retVal = superBldr.build();
        } else {
            throw new FailedException("Invalid node type");
        }
        if (retVal != null) {
            eventFuture = CompletableFuture.completedFuture(retVal);
        } else {
            log.info("STORE: Failed to READ node");
        }
        return eventFuture;
    }

    private void readInner(DataNode.Builder superBldr, String spath) {
        CompletableFuture<Map<String, Versioned<DataNode.Type>>> ret = keystore.getChildren(
                toAtomixPath(DocumentPath.from(spath)));
        Map<String, Versioned<DataNode.Type>> entries = null;
        entries = complete(ret);

        log.trace(" keystore.getChildren({})", spath);
        log.trace("  entries keys:{}", entries.keySet());
        if (!entries.isEmpty()) {
            entries.forEach((k, v) -> {
                String[] names = k.split(ResourceIdParser.NM_CHK);
                String name = names[0];
                String nmSpc = ResourceIdParser.getNamespace(names[1]);
                String keyVal = ResourceIdParser.getKeyVal(names[1]);
                DataNode.Type type = v.value();
                String tempPath = ResourceIdParser.appendNodeKey(spath, name, nmSpc);
                if (type == DataNode.Type.SINGLE_INSTANCE_LEAF_VALUE_NODE) {
                    LeafNode lfnode = readLeaf(tempPath);
                    // FIXME there should be builder for copying
                    superBldr.createChildBuilder(name, nmSpc, lfnode.value(), lfnode.valueNamespace())
                            .type(type)
                            .leafType(lfnode.leafType())
                            .exitNode();
                } else if (type == DataNode.Type.MULTI_INSTANCE_LEAF_VALUE_NODE) {
                    String mlpath = ResourceIdParser.appendLeafList(tempPath, keyVal);
                    LeafNode lfnode = readLeaf(mlpath);
                    // FIXME there should be builder for copying
                    superBldr.createChildBuilder(name, nmSpc, lfnode.value(), lfnode.valueNamespace())
                            .type(type)
                            .leafType(lfnode.leafType())
                            .addLeafListValue(lfnode.value())
                            .exitNode();
                    //TODO this alone should be sufficient and take the nm, nmspc too
                } else if (type == DataNode.Type.SINGLE_INSTANCE_NODE) {
                    DataNode.Builder tempBldr = superBldr.createChildBuilder(name, nmSpc)
                            .type(type);
                    readInner(tempBldr, tempPath);
                } else if (type == DataNode.Type.MULTI_INSTANCE_NODE) {
                    DataNode.Builder tempBldr = superBldr.createChildBuilder(name, nmSpc)
                            .type(type);
                    tempPath = ResourceIdParser.appendMultiInstKey(tempPath, k);
                    String[] keys = k.split(ResourceIdParser.KEY_CHK);
                    for (int i = 1; i < keys.length; i++) {
                        //String curKey = ResourceIdParser.appendKeyLeaf(tempPath, keys[i]);
                        //LeafNode lfnd = readLeaf(curKey);
                        String[] keydata = keys[i].split(ResourceIdParser.NM_CHK);
                        tempBldr.addKeyLeaf(keydata[0], keydata[1], keydata[2]);
                    }
                    readInner(tempBldr, tempPath);
                } else {
                    throw new FailedException("Invalid node type");
                }
            });
        }
        superBldr.exitNode();
    }

    private LeafNode readLeaf(String path) {
        return (LeafNode) map.get(path);
    }

    private void parseNode(String path, DataNode node) {
        if (completeVersioned(keystore.get(toAtomixPath(DocumentPath.from(path)))) != null) {
            throw new FailedException("Requested node already present in the" +
                    " store, please use an update method");
        }
        if (node.type() == DataNode.Type.SINGLE_INSTANCE_LEAF_VALUE_NODE) {
            addLeaf(path, (LeafNode) node);
        } else if (node.type() == DataNode.Type.MULTI_INSTANCE_LEAF_VALUE_NODE) {
            if (completeVersioned(keystore.get(toAtomixPath(DocumentPath.from(path)))) != null) {
                throw new FailedException("Requested node already present in the" +
                        " store, please use an update method");
            }
            addLeaf(path, (LeafNode) node);
        } else if (node.type() == DataNode.Type.SINGLE_INSTANCE_NODE) {
            traverseInner(path, (InnerNode) node);
        } else if (node.type() == DataNode.Type.MULTI_INSTANCE_NODE) {
            if (completeVersioned(keystore.get(toAtomixPath(DocumentPath.from(path)))) != null) {
                throw new FailedException("Requested node already present in the" +
                        " store, please use an update method");
            }
            traverseInner(path, (InnerNode) node);
        } else {
            throw new FailedException("Invalid node type");
        }
    }

    public static class Builder {

        public static YangService build() {
            return new YangService(new AtomixManager());
        }
    }

    public ResourceId toResourceId(String path) {
        if (null == path) {
            throw new NullPointerException();
        }
        ResourceId resourceId = ResourceId.builder().build();
        String[] args1 = path.split("\\/");
        for (String arg : args1) {
            if (!arg.contains("$")) {
                String[] args2 = arg.split("#");
                try {
                    resourceId = resourceId.copyBuilder().addBranchPointSchema(args2[0], args2[1]).build();
                } catch (CloneNotSupportedException e) {
                    throw new FailedException();
                }
            } else {
                String[] args3 = arg.split("$");

            }

        }
        return resourceId;
    }


    public static class InnerListenner implements DocumentTreeListener<DataNode.Type> {

        @Override
        public void event(DocumentTreeEvent<DataNode.Type> event) {
            DynamicConfigEvent.Type type;
            ResourceId path;
            switch (event.type()) {
                case CREATED:
                    type = NODE_ADDED;
                    //log.info("NODE added in store");
                    break;
                case UPDATED:
                    //log.info("NODE updated in store");
                    type = NODE_UPDATED;
                    break;
                case DELETED:
                    //log.info("NODE deleted in store");
                    type = NODE_DELETED;
                    break;
                default:
                    //log.info("UNKNOWN operation in store");
                    type = UNKNOWN_OPRN;
            }
            // FIXME don't use ResourceIdParser
            path = ResourceIdParser.getResId(event.path().pathElements());
            System.out.println(path);
//            notifyDelegate(new DynamicConfigEvent(type, path));
        }
    }


//    public static void main(String[] args) {
//        YangRuntimeService yangRuntimeService = new DefaultYangRuntimeHandler()
//    }
}
