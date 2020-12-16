package com.example.java.yangtools;


import com.example.java.JavaApplicationTests;
import com.example.java.netconf.NetconfTranslator;
import com.example.java.yangtools.YangService;
import org.junit.Test;
import org.onosproject.net.DeviceId;
import org.onosproject.yang.model.DataNode;
import org.onosproject.yang.model.InnerNode;
import org.onosproject.yang.model.ResourceId;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


public class YangServiceTest extends JavaApplicationTests {

    @Autowired
    NetconfTranslator netconfTranslator;


    DataNode node = InnerNode.builder("devices", "org.onosproject.dcs")
            .type(DataNode.Type.SINGLE_INSTANCE_NODE)
//                .addNode(InnerNode.builder("device", "DCS_NAMESPACE")
//                        .type(DataNode.Type.MULTI_INSTANCE_NODE)
//                        .addKeyLeaf("device-id", "DCS_NAMESPACE", "netconf@10.5.51.42:830")
//                        .build())
            .build();
    ResourceId path = ResourceId.builder()
            .addBranchPointSchema("/", "org.onosproject.dcs")
//                .addBranchPointSchema("devices", "org.onosproject.dcs")
//                .addBranchPointSchema("device", "org.onosproject.dcs")
//                .addKeyLeaf("device-id", "org.onosproject.dcs", "netconf:10.5.51.42:830")
            .build();

    @Autowired
    YangService yangService;


    @Test
    public void addNodeTest() {


        yangService.addNode(path,node);

    }
    @Test
    public void readNode(){
        yangService.addNode(path,node);

        System.out.println(yangService.readNode(path,null).join());
    }
    @Test
    public void test(){
        try {
            netconfTranslator.getDeviceConfig(DeviceId.deviceId("netconf:10.5.51.42:830"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}