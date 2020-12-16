package com.example.java;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.onosproject.yang.model.InnerNode;

import java.util.List;

public class DataNode {
    private String name;
    private String nameSpace;

    public class Build{

        org.onosproject.yang.model.DataNode build(ObjectNode objectNode){
            InnerNode node = InnerNode.builder("/","").build();
            return null;
        }

    }


}
