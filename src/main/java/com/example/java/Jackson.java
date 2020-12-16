package com.example.java;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.onosproject.yang.runtime.YangRuntimeService;
import org.onosproject.yang.runtime.impl.DefaultYangRuntimeHandler;

import java.io.IOException;
import java.io.InputStream;

public class Jackson {

    public static void main(String[] args) {

//        YangRuntimeService yangRuntimeService = new DefaultYangRuntimeHandler();
        ObjectMapper mapper = new ObjectMapper();
        InputStream stream = InputStream.nullInputStream();

        try {
            ObjectNode node = (ObjectNode) mapper.readTree(stream);
            System.out.println(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
