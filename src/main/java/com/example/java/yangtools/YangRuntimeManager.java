package com.example.java.yangtools;

import org.onosproject.yang.model.ModelConverter;
import org.onosproject.yang.model.ModelObjectData;
import org.onosproject.yang.model.ModelObjectId;
import org.onosproject.yang.model.NodeKey;
import org.onosproject.yang.model.ResourceData;
import org.onosproject.yang.model.ResourceId;
import org.onosproject.yang.model.RpcContext;
import org.onosproject.yang.model.SchemaContext;
import org.onosproject.yang.model.SchemaContextProvider;
import org.onosproject.yang.model.YangModel;
import org.onosproject.yang.model.YangModule;
import org.onosproject.yang.model.YangModuleId;
import org.onosproject.yang.runtime.CompositeData;
import org.onosproject.yang.runtime.CompositeStream;
import org.onosproject.yang.runtime.ModelRegistrationParam;
import org.onosproject.yang.runtime.RuntimeContext;
import org.onosproject.yang.runtime.YangModelRegistry;
import org.onosproject.yang.runtime.YangRuntimeService;
import org.onosproject.yang.runtime.YangSerializer;
import org.onosproject.yang.runtime.YangSerializerRegistry;
import org.onosproject.yang.runtime.impl.DefaultModelConverter;
import org.onosproject.yang.runtime.impl.DefaultSchemaContextProvider;
import org.onosproject.yang.runtime.impl.DefaultYangModelRegistry;
import org.onosproject.yang.runtime.impl.DefaultYangRuntimeHandler;
import org.onosproject.yang.runtime.impl.DefaultYangSerializerRegistry;
import org.onosproject.yang.serializers.json.JsonSerializer;
import org.onosproject.yang.serializers.xml.XmlSerializer;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class YangRuntimeManager implements YangModelRegistry, YangSerializerRegistry,
        YangRuntimeService, ModelConverter, SchemaContextProvider,YangClassLoaderRegistry {


    private static final String APP_ID = "org.onosproject.yang";
    private final Logger log = LoggerFactory.getLogger(getClass());


    private DefaultYangModelRegistry modelRegistry;
    private DefaultYangSerializerRegistry serializerRegistry;
    private DefaultYangRuntimeHandler runtimeService;
    private DefaultModelConverter modelConverter;
    private DefaultSchemaContextProvider schemaContextProvider;

    private Map<String, ClassLoader> classLoaders;

    public YangRuntimeManager(){
        serializerRegistry = new DefaultYangSerializerRegistry();
        modelRegistry = new DefaultYangModelRegistry();
        runtimeService = new DefaultYangRuntimeHandler(serializerRegistry, modelRegistry);
        schemaContextProvider = new DefaultSchemaContextProvider(modelRegistry);
        serializerRegistry.registerSerializer(new JsonSerializer());
        serializerRegistry.registerSerializer(new XmlSerializer());
        modelConverter = new DefaultModelConverter(modelRegistry);
        classLoaders = new ConcurrentHashMap<>();
        log.info("Started");
    }
    @Override
    public void registerModel(ModelRegistrationParam p) {
        modelRegistry.registerModel(p);
    }

    @Override
    public void unregisterModel(ModelRegistrationParam p) {
        modelRegistry.unregisterModel(p);
    }

    @Override
    public Set<YangModel> getModels() {
        return modelRegistry.getModels();
    }

    @Override
    public YangModel getModel(String s) {
        return modelRegistry.getModel(s);
    }

    @Override
    public YangModule getModule(YangModuleId yangModuleId) {
        return modelRegistry.getModule(yangModuleId);
    }

    @Override
    public void registerSerializer(YangSerializer ys) {
        serializerRegistry.registerSerializer(ys);
    }

    @Override
    public void unregisterSerializer(YangSerializer ys) {
        serializerRegistry.unregisterSerializer(ys);
    }

    @Override
    public Set<YangSerializer> getSerializers() {
        return serializerRegistry.getSerializers();
    }

    @Override
    public CompositeData decode(CompositeStream cs, RuntimeContext rc) {
        return runtimeService.decode(cs, rc);
    }

    @Override
    public CompositeStream encode(CompositeData cd, RuntimeContext rc) {
        return runtimeService.encode(cd, rc);
    }

    @Override
    public ModelObjectData createModel(ResourceData resourceData) {
        return modelConverter.createModel(resourceData);
    }

    @Override
    public ResourceData createDataNode(ModelObjectData modelObjectData) {
        return modelConverter.createDataNode(modelObjectData);
    }

    @Override
    public void registerAnydataSchema(ModelObjectId arg0, ModelObjectId arg1)
            throws IllegalArgumentException {
        throw new UnsupportedOperationException("registerAnydataSchema() needs to be implemented");
    }

    @Override
    public void unregisterAnydataSchema(Class id, Class id1) {
        throw new UnsupportedOperationException("unregisterAnydataSchema() needs to be implemented");

    }

    @Override
    public SchemaContext getSchemaContext(ResourceId resourceId) {
        NodeKey key = resourceId.nodeKeys().get(0);
        if (resourceId.nodeKeys().size() == 1 &&
                "/".equals(key.schemaId().name())) {
            return modelRegistry;
        }
        log.info("To be implemented.");
        return null;
    }

    @Override
    public RpcContext getRpcContext(ResourceId resourceId) {
        return schemaContextProvider.getRpcContext(resourceId);
    }

    @Override
    public ClassLoader getClassLoader(String modelId) {
        return classLoaders.get(modelId);
    }

    @Override
    public void registerClassLoader(String modelId, ClassLoader classLoader) {
        classLoaders.put(modelId, classLoader);
    }

    @Override
    public void unregisterClassLoader(String modelId) {
        classLoaders.remove(modelId);
    }
}
