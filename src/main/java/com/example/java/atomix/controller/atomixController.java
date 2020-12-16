package com.example.java.atomix.controller;


import com.example.java.atomix.Utils;
import com.example.java.restconf.MediaTypeRestconf;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.onosproject.yang.model.*;
import org.onosproject.yang.runtime.*;
import org.onosproject.yang.runtime.impl.DefaultYangModelRegistry;
import org.onosproject.yang.runtime.impl.DefaultYangRuntimeHandler;
import org.onosproject.yang.runtime.impl.DefaultYangSerializerRegistry;
import org.onosproject.yang.serializers.json.JsonSerializer;
import org.onosproject.yang.serializers.xml.XmlSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

@RestController("/atomixController")
public class atomixController {

    ObjectMapper mapper = new ObjectMapper();


    @GetMapping
    public String atomixGet() {

        return "success!";
    }

    @PostMapping()
    @Consumes({MediaTypeRestconf.APPLICATION_YANG_DATA_JSON, MediaType.APPLICATION_JSON})
    @Produces(MediaTypeRestconf.APPLICATION_YANG_DATA_JSON)
    public Object atomixPost(ServletRequest request, InputStream stream) {
//        URI uri = request.
        ObjectNode objectNode = null;
        try {
//            DataNode dataNode = InnerNode.builder("/", "").build();

            objectNode = Utils.readTreeFromStream(mapper, stream);

            ResourceData resourceData = convertJsonToDataNode("/", objectNode);
            ResourceId rid = resourceData.resourceId();
            List<DataNode> dataNodeList = resourceData.dataNodes();
            if (dataNodeList == null || dataNodeList.isEmpty()) {
                System.out.println("There is no one Data Node can be proceed.");

            }
            if (dataNodeList.size() > 1) {
                System.out.println("There are more than one Data Node can be proceed");
            }
            DataNode dataNode = dataNodeList.get(0);
            objectNode.fields().next().getValue().asText().contains("\\{");
            String json = objectNode.toString();
            InputStream inputStream = IOUtils.toInputStream(json, Charset.defaultCharset());
            objectNode = Utils.readTreeFromStream(mapper, inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectNode;
    }


    public ResourceData convertJsonToDataNode(String path, ObjectNode node) {

        YangSerializerRegistry serializerRegistry = new DefaultYangSerializerRegistry();
        serializerRegistry.registerSerializer(new JsonSerializer());
        serializerRegistry.registerSerializer(new XmlSerializer());

        YangRuntimeService yangRuntimeService
                = new DefaultYangRuntimeHandler(serializerRegistry, new DefaultYangModelRegistry());

//        YangRuntimeService yangRuntimeService = new DefaultYangRuntimeHandler();
        RuntimeContext context = new DefaultRuntimeContext.Builder().setDataFormat("JSON").build();
        InputStream stream = IOUtils.toInputStream(node.toString(), Charset.defaultCharset());

        CompositeStream compositeStream = new DefaultCompositeStream(path, stream);
        CompositeData compositeData = yangRuntimeService.decode(compositeStream, context);
        return compositeData.resourceData();
    }

}
