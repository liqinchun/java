package com.example.java.atomix;

import com.example.java.yangtools.DeviceResourceIds;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.core.map.AtomicMap;
import io.atomix.core.tree.AsyncAtomicDocumentTree;
import io.atomix.core.tree.AtomicDocumentTree;
import io.atomix.core.tree.DocumentPath;
import io.atomix.core.tree.DocumentTreeEvent;
import io.atomix.core.tree.DocumentTreeEventListener;
import io.atomix.core.tree.IllegalDocumentModificationException;
import io.atomix.primitive.partition.impl.DefaultPartitionGroupMembershipService;
import io.atomix.protocols.backup.MultiPrimaryProtocol;
import io.atomix.protocols.backup.partition.PrimaryBackupPartitionGroup;
import io.atomix.protocols.raft.MultiRaftProtocol;
import io.atomix.protocols.raft.ReadConsistency;
import io.atomix.protocols.raft.partition.RaftPartitionGroup;
import io.atomix.utils.event.Event;
import io.atomix.utils.time.Versioned;
import org.onosproject.yang.model.DataNode;
import org.onosproject.yang.model.DefaultResourceData;
import org.onosproject.yang.model.InnerNode;
import org.onosproject.yang.model.LeafNode;
import org.onosproject.yang.model.LeafType;
import org.onosproject.yang.model.ResourceData;
import org.onosproject.yang.model.ResourceId;

import java.awt.image.ImageProducer;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MainClass {

    static {

    }


//    public static void main(String[] args) throws Exception {
//
//        Atomix atomix = Atomix.builder()
//                .withMemberId("member-1")
//                .withAddress("10.5.51.42:5679")
//                .withMembershipProvider(BootstrapDiscoveryProvider.builder()
//                        .withNodes(Node.builder()
//                                .withId("member1")
//                                .withAddress("10.5.51.42:5679")
//                                .build())
//                        .build())
//                .withManagementGroup(PrimaryBackupPartitionGroup.builder("system")
//                        .withNumPartitions(1)
//                        .build())
//                .withPartitionGroups(PrimaryBackupPartitionGroup.builder("data")
//                        .withNumPartitions(32)
//                        .build())
//                .build();
//        atomix.start().join();
//
//        AtomicDocumentTree<String> tree = atomix.<String>atomicDocumentTreeBuilder("my-tree")
//                .withProtocol(MultiPrimaryProtocol.builder()
//                                      .build())
//                .build();
//        atomix.getAtomicDocumentTree("my-tree").async();
////        tree.set("/","ddddd");
//        AsyncAtomicDocumentTree asyncAtomicDocumentTree = tree.async();
//        asyncAtomicDocumentTree.create("/|foo|bar", "ddddc").join();
//        asyncAtomicDocumentTree.get("/|foo|bar");
//        asyncAtomicDocumentTree.get("/|foo|bar");
//        Versioned<String> value = tree.get(DocumentPath.from("/|foo|bar"));
//        System.out.println(value);
//        System.out.println(asyncAtomicDocumentTree.get("/|foo|ba"));
//        System.out.println(asyncAtomicDocumentTree.name());
//        DataNode rootNode = LeafNode.builder(DeviceResourceIds.ROOT_NAME, DeviceResourceIds.DCS_NAMESPACE)
//                .type(DataNode.Type.SINGLE_INSTANCE_NODE).build();
//
//        List arrayList = new ArrayList();
////        ArrayList<String> list =  Arrays.asList("aa|a|a".split("\\|"));
////        System.out.println(list.add(""));
//        DataNode node = InnerNode.builder("devices", "DCS_NAMESPACE")
//                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
//                .addNode(InnerNode.builder("device", "DCS_NAMESPACE")
//                                 .type(DataNode.Type.MULTI_INSTANCE_NODE)
//                                  .addKeyLeaf("device-id","DCS_NAMESPACE","netconf@10.5.51.42:830")
//                                 .build())
//                .build();
//        ResourceId resourceId = ResourceId.builder().addBranchPointSchema("devices", "devices").build();
//        ResourceData resourceData = DefaultResourceData.builder().addDataNode(node).resourceId(resourceId).build();
//        asyncAtomicDocumentTree.set("/|devices|device", node);
//        try {
//            asyncAtomicDocumentTree.get(DocumentPath.from("/|devices|device")).get();
//            asyncAtomicDocumentTree.get("/|devices|device|ddd").join();
//            String path = "root|get-config#urn:ietf:params:xml:ns:netconf:base:1.0|get-config#urn:ietf:params:xml:ns:netconf:base:1.0";
//            List<String> pathList = new ArrayList<>();
//            pathList.add("root");
////            pathList.add("get-config#urn:ietf:params:xml:ns:netconf:base:1.0");
////            pathList.add("get-config#urn:ietf:params:xml:ns:netconf:base:1.0");
//            DocumentPath documentPath = DocumentPath.from(pathList);
//            pathList.add("get-config#urn:ietf:params:xml:ns:netconf:base:1.0");
//            DocumentPath documentPath1 = DocumentPath.from(pathList);
//            pathList.add("get-config#urn:ietf:params:xml:ns:netconf:base:1.1");
//            DocumentPath documentPath2 = DocumentPath.from(pathList);
//            try {
//                asyncAtomicDocumentTree.set(documentPath, DataNode.Type.SINGLE_INSTANCE_NODE).get();
//                asyncAtomicDocumentTree.set(documentPath1, DataNode.Type.SINGLE_INSTANCE_NODE).get();
//                asyncAtomicDocumentTree.set(documentPath2, DataNode.Type.SINGLE_INSTANCE_NODE).get();
//                asyncAtomicDocumentTree.get("");
//                System.out.println(asyncAtomicDocumentTree.get(documentPath2).get().toString());
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                throw new InterruptedException(e.getCause().getMessage());
//            } catch (ExecutionException e) {
//                if (e.getCause() instanceof IllegalDocumentModificationException) {
//                    throw new ExecutionException("Node or parent does not exist or is root or is not a Leaf Node",
//                                                 e.getCause());
//                }
//            }
//            arrayList.add("");
//            arrayList.add("1222222");
//            arrayList.set(0, "root");
//            arrayList.remove(0);
//            System.out.println(arrayList);
//            System.out.println("aaaa".contains("|"));
//
//            Object children = asyncAtomicDocumentTree.getChildren(DocumentPath.from("/")).join();
//            Object children1 = asyncAtomicDocumentTree.getChildren(DocumentPath.from("/root")).join();
//            Object children2 = asyncAtomicDocumentTree.getChildren(DocumentPath.from("/root/get-config#urn:ietf:params:xml:ns:netconf:base:1.0")).join();
//            System.out.println(children);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            InputStream in ;
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

    public static AsyncAtomicDocumentTree getTree(){
        return null;
    }
}

