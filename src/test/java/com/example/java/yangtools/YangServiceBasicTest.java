package com.example.java.yangtools;

import com.example.java.exception.FailedException;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.junit.Test;
import org.onosproject.yang.model.*;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

public class YangServiceBasicTest {




    DataNode node = InnerNode.builder("devices", "org.onosproject.dcs")
            .type(DataNode.Type.SINGLE_INSTANCE_NODE)
//                .addNode(InnerNode.builder("device", "DCS_NAMESPACE")
//                        .type(DataNode.Type.MULTI_INSTANCE_NODE)
//                        .addKeyLeaf("device-id", "DCS_NAMESPACE", "netconf@10.5.51.42:830")
//                        .build())
            .build();
    ResourceId rootPath = ResourceId.builder()
            .addBranchPointSchema("/", "")
//                .addBranchPointSchema("devices", "org.onosproject.dcs")
//                .addBranchPointSchema("device", "org.onosproject.dcs")
//                .addKeyLeaf("device-id", "org.onosproject.dcs", "netconf:10.5.51.42:830")
            .build();
    ResourceId dedeviceesPath = ResourceId.builder()
            .addBranchPointSchema("/", "")
            .addBranchPointSchema("devices", "org.onosproject.dcs")
//                .addBranchPointSchema("device", "org.onosproject.dcs")
//                .addKeyLeaf("device-id", "org.onosproject.dcs", "netconf:10.5.51.42:830")
            .build();
//    YangService yangService = YangService.Builder.build();
    YangService yangService ;

    @Test
    public void addNodeTest() {
        DataNode devicesNode = InnerNode.builder("devices", "org.onosproject.dcs")
                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                .addNode(InnerNode.builder("device", "org.onosproject.dcs")
                        .type(DataNode.Type.MULTI_INSTANCE_NODE)
                        .addKeyLeaf("device-id", "org.onosproject.dcs", "netconf@10.5.51.42:830")
                        .addKeyLeaf("device-id", "org.onosproject.dcs", "netconf@10.5.51.43:830")
                        .build())
                .build();
        ResourceId devicesPath = ResourceId.builder()
                .addBranchPointSchema("/", "org.onosproject.dcs")
//                .addBranchPointSchema("devices", "org.onosproject.dcs")
//                .addBranchPointSchema("device", "org.onosproject.dcs")
//                .addKeyLeaf("device-id", "org.onosproject.dcs", "netconf:10.5.51.42:830")
                .build();
        StopWatch watch = new StopWatch();
        watch.start();

//        yangService.addNode(path, node);
        yangService.addNode(devicesPath, devicesNode);
        watch.stop();
        System.out.println(watch.getTotalTimeSeconds());
    }

    @Test
    public void toResourceIdTest() {
        String str = "/#org.onosproject.dcs/devices#org.onosproject.dcs/device#org.onosproject.dcs$device-id#" +
                "org.onosproject.dcs#netconf@10.5.51.42:830$device-id#org.onosproject.dcs#netconf@10.5.51.43:830";


    }

    @Test
    public void getNode() {

        String s = "root|devices#org.onosproject.dcs|" +
                "device#org.onosproject.dcs$deviceid#org.onosproject.dcs#netconf:172.16.5.11:22|" +
                "l3vpn#org.onosproject.dcs|l3vpncomm#org.onosproject.dcs|l3vpnInstances#org.onosproject.dcs|" +
                "l3vpnInstance#org.onosproject.dcs$vrfName#org.onosproject.dcs#vrf2|l3vpnIfs#org.onosproject.dcs";
        String ip = "/devices#org.onosproject.dcs/" +
                "device#org.onosproject.dcs$deviceid#org.onosproject.dcs#netconf:10.5.51.42:830/" +
                "l3vpn#org.onosproject.dcs/l3vpncomm#org.onosproject.dcs/l3vpnInstances#org.onosproject.dcs/" +
                "l3vpnInstance#org.onosproject.dcs$vrfName#org.onosproject.dcs#vrf3";
        ResourceId resourceId = ResourceId.builder()
//                .addBranchPointSchema("root","")
                .addBranchPointSchema("devices", "org.onosproject.dcs")
                .addBranchPointSchema("device", "org.onosproject.dcs")
                .addKeyLeaf("deviceid", "org.onosproject.dcs", "netconf:172.16.5.11:22")
                .addBranchPointSchema("l3vpn", "org.onosproject.dcs")
                .addBranchPointSchema("l3vpncomm", "org.onosproject.dcs")
                .addBranchPointSchema("l3vpnInstances", "org.onosproject.dcs")
                .addBranchPointSchema("l3vpnInstance", "org.onosproject.dcs")
                .addKeyLeaf("vrfName", "org.onosproject.dcs", "vrf2")
                .addBranchPointSchema("l3vpnIfs", "org.onosproject.dcs")
                .build();

        DataNode devicesNode = InnerNode.builder("devices", "org.onosproject.dcs")
                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                .addNode(InnerNode.builder("device", "org.onosproject.dcs")
                        .type(DataNode.Type.MULTI_INSTANCE_NODE)
                        .addKeyLeaf("deviceid", "org.onosproject.dcs", "netconf:172.16.5.11:22")
                        .addNode(InnerNode.builder("l3vpn", "org.onosproject.dcs")
                                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                                .addNode(InnerNode.builder("l3vpncomm", "org.onosproject.dcs")
                                        .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                                        .addNode(InnerNode.builder("l3vpnInstances", "org.onosproject.dcs")
                                                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                                                .addNode(InnerNode.builder("l3vpnInstance", "org.onosproject.dcs")
                                                        .type(DataNode.Type.MULTI_INSTANCE_NODE)
                                                        .addKeyLeaf("vrfName", "org.onosproject.dcs", "vrf2")
                                                        .addNode(InnerNode.builder("l3vpnIfs","org.onosproject.dcs")
                                                                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                                                                .build())
                                                .build())
                                                .build())
                                        .build())
                                .build())
                        .build()

                ).build();



        DataNode devicesNode1 = InnerNode.builder("device", "org.onosproject.dcs")
                .type(DataNode.Type.MULTI_INSTANCE_NODE)
                .addKeyLeaf("deviceid", "org.onosproject.dcs", "netconf:10.5.51.42:830")
                .addNode(InnerNode.builder("l3vpn", "org.onosproject.dcs")
                        .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                        .addNode(InnerNode.builder("l3vpncomm", "org.onosproject.dcs")
                                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                                .addNode(InnerNode.builder("l3vpnInstances", "org.onosproject.dcs")
                                        .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                                        .addNode(InnerNode.builder("l3vpnInstance", "org.onosproject.dcs")
                                                .type(DataNode.Type.MULTI_INSTANCE_NODE)
                                                .addKeyLeaf("vrfName", "org.onosproject.dcs", "vrf3")
                                                .addNode(LeafNode.builder("l3vpnIfs111111","org.onosproject.dcs")
                                                        .value("ddddddddddsa11111")
                                                        .type(DataNode.Type.SINGLE_INSTANCE_LEAF_VALUE_NODE)
                                                        .build())
                                                .addNode(LeafNode.builder("l3vpnIf22222","org.onosproject.dcs")
                                                        .value("dddddddd333")
                                                        .type(DataNode.Type.SINGLE_INSTANCE_LEAF_VALUE_NODE)
                                                        .build())
                                                .addNode(InnerNode.builder("l3vpnI22","org.onosproject.dcs")
                                                        .type(DataNode.Type.MULTI_INSTANCE_NODE)
                                                        .addKeyLeaf("l3vpnI2221321","org.onosproject.dcs","value")
                                                        .addKeyLeaf("l3vpnI2221321","org.onosproject.dcs","value333")
                                                        .build())
                                                .addNode(LeafNode.builder("l3vpnIf222333","org.onosproject.dcs")
                                                        .value("dddddddd333")
                                                        .type(DataNode.Type.SINGLE_INSTANCE_LEAF_VALUE_NODE)
                                                        .build())
                                                .addKeyLeaf("vrfName", "org.onosproject.dcs", "vrf4")

                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        ResourceId l3DevicesResourceId = ResourceId.builder()
//                .addBranchPointSchema("root","")
                .addBranchPointSchema("devices", "org.onosproject.dcs")
//                .addBranchPointSchema("device", "org.onosproject.dcs")
//                .addKeyLeaf("deviceid", "org.onosproject.dcs", "netconf:172.16.5.11:22")
//                .addBranchPointSchema("l3vpn", "org.onosproject.dcs")
//                .addBranchPointSchema("l3vpncomm", "org.onosproject.dcs")
//                .addBranchPointSchema("l3vpnInstances", "org.onosproject.dcs")
//                .addBranchPointSchema("l3vpnInstance", "org.onosproject.dcs")
//                .addKeyLeaf("vrfName", "org.onosproject.dcs", "vrf2")
//                .addBranchPointSchema("l3vpnIfs", "org.onosproject.dcs")
                .build();
//        String path = ResourceIdParser.parseResId(resourceId);
//        DocumentPath dpath = DocumentPath.from(path);
//        ResourceIdParser.toAtomixPath(dpath);
        DataNode node1 = InnerNode.builder("node","12333")
                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                .addNode(InnerNode.builder("nodeA","12333")
                        .type(DataNode.Type.MULTI_INSTANCE_NODE)
                        .addKeyLeaf("nodeA-1","12333","aaaaa11111")
                        .addKeyLeaf("nodeA-2","12333","aaaaa11111")
                        .build())
                .build();

        DataNode node2 = InnerNode.builder("nodeA","12333")
                .type(DataNode.Type.MULTI_INSTANCE_NODE)
                .addKeyLeaf("nodeA-2","12333","aaaaa22222").build();
//        yangService.addNode(rootPath, node1);
//        yangService.addNode(rootPath, node2);
        yangService.addNode(rootPath, devicesNode);
        yangService.addNode(rootPath, devicesNode1);
        DataNode node = yangService.readNode(dedeviceesPath, null).join();
        System.out.println(node);
    }

    public ResourceId toResourceId(String path) {
        if (null == path) {
            throw new NullPointerException();
        }
        ResourceId resourceId = ResourceId.builder().build();
        String[] args1 = path.split("\\/");
        for (String arg : args1) {
            if (!StringUtils.isEmpty(arg))
                if (!arg.contains("$")) {
                    String[] args2 = arg.split("#");
                    try {
                        resourceId = resourceId.copyBuilder().addBranchPointSchema(args2[0], args2[1]).build();
                    } catch (CloneNotSupportedException e) {
                        throw new FailedException();
                    }
                } else {
                    String[] args3 = arg.split("\\$");
                    for (String s : args3) {
                        String[] args4 = s.split("#");
                        try {
                            if (!s.contains(":")) {
                                resourceId = resourceId.copyBuilder().addBranchPointSchema(args4[0], args4[1]).build();

                            } else {
                                resourceId = resourceId.copyBuilder().addLeafListBranchPoint(args4[0], args4[1], args4[2]).build();
                            }
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        }
        return resourceId;
    }

    @Test
    public void test02(){
        String path = "root|uplink-services#unknown:uri|uplink-svc#unknown:uri$device-id#unknown:uri#netconf:52.53.172.49:830|wired#unknown:uri|wan#unknown:uri$name#unknown:uri#wan1|ip-origin#unknown:uri";
        String[] el = path.split(ResourceIdParser.EL_CHK);

        String ipad = "uplink-svc#unknown:uri$device-id#unknown:uri#netconf:52.53.172.49:830";
        System.out.println();

    }

//    public DeviceId getDeviceId(ResourceId path) {
//        String resId = ResourceIdParser.parseResId(path);
//        String[] el = resId.split(ResourceIdParser.EL_CHK);
//        if (el.length < 3) {
//            throw new IllegalStateException(new NetconfException("Invalid resource id, cannot apply"));
//        }
//        if (!el[2].contains((ResourceIdParser.KEY_SEP))) {
//            throw new IllegalStateException(new NetconfException("Invalid device id key, cannot apply"));
//        }
//        String[] keys = el[2].split(ResourceIdParser.KEY_CHK);
//        if (keys.length < 2) {
//            throw new IllegalStateException(new NetconfException("Invalid device id key, cannot apply"));
//        }
//        String[] parts = keys[1].split(ResourceIdParser.NM_CHK);
//        if (parts.length < 3) {
//            throw new IllegalStateException(new NetconfException("Invalid device id key, cannot apply"));
//        }
//        String[] temp = parts[2].split("\\:");
//        String ip, port;
//        if (temp.length != 3) {
//            throw new IllegalStateException(new NetconfException("Invalid device id form, cannot apply"));
//        }
//        ip = temp[1];
//        port = temp[2];
//        try {
//            return DeviceId.deviceId(new URI("netconf", ip + ":" + port, (String) null));
//        } catch (URISyntaxException ex) {
//            throw new IllegalArgumentException("Unable to build deviceID for device " + ip + ":" + port, ex);
//        }
//    }


    @Test
    public void test01(){
        String ifNamespace = "urn:ietf:params:xml:ns:yang:ietf-interfaces";
        String ipNamespace = "urn:ietf:params:xml:ns:yang:ietf-ip";
        DataNode dataNode = InnerNode.builder("devices",ifNamespace)
                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                .addNode(InnerNode.builder("device", ifNamespace)
                        .type(DataNode.Type.MULTI_INSTANCE_NODE)
                        .addKeyLeaf("deviceid",ifNamespace,"10.5.51.42:830")
                        .addNode(InnerNode.builder("interfaces", ifNamespace)
                                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                                .addNode(InnerNode.builder("interface",ifNamespace)
                                        .type(DataNode.Type.MULTI_INSTANCE_NODE)
                                        .addKeyLeaf("name",ifNamespace,"wan1")
                                        .addKeyLeaf("description",ifNamespace,"wan1")
                                        .addKeyLeaf("type",ifNamespace,"ianaift:ethernetCsmacd")
                                        .addKeyLeaf("enabled",ifNamespace,true)
                                        .addNode(InnerNode.builder("ipv4",ipNamespace)
                                                .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                                                .addKeyLeaf("enabled",ipNamespace,true)
                                                .addKeyLeaf("forwarding",ipNamespace,false)
                                                .addNode(InnerNode.builder("address",ipNamespace)
                                                        .type(DataNode.Type.SINGLE_INSTANCE_NODE)
                                                        .addKeyLeaf("ip",ipNamespace,"10.5.51.42")
                                                        .addKeyLeaf("prefix-length",ipNamespace,"24")
                                                        .build())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        System.out.println(node);
    }


    @Test
    public void test03(){
        InnerNode dataNode =InnerNode.builder("device","org.inhand").type(DataNode.Type.MULTI_INSTANCE_NODE)
                        .addKeyLeaf("name","org.inhand","aaaaaa")
                .build();
        DataNode dataNode1 = InnerNode.builder("interfaces","org.inhand").type(DataNode.Type.SINGLE_INSTANCE_NODE).build();
        DataNode dataNode2 = InnerNode.builder("dhcp","org.inhand").type(DataNode.Type.SINGLE_INSTANCE_NODE).build();
        dataNode.childNodes().put(dataNode1.key(),dataNode1);
        dataNode.childNodes().put(dataNode2.key(),dataNode2);
        System.out.println(dataNode);
        ResourceId resourceId = ResourceId.builder().addBranchPointSchema("/",null)
                .addBranchPointSchema("devices","org.inhand")
                .build();
        NodeKey nodeKey = NodeKey.builder().schemaId("name","org.inhand").build();
        dataNode.copyBuilder().key(nodeKey);
        try {
            ResourceId resourceId1 = resourceId.copyBuilder().addBranchPointSchema(dataNode.key().schemaId().name(),dataNode.key().schemaId().name()).build();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }



}

