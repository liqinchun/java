package com.example.java.atomix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;

import static org.onlab.util.Tools.nullIsIllegal;

public class Utils {

    private static final String INPUT_JSON_CANNOT_BE_NULL = "Input JSON cannot be null";

    public static ObjectNode readTreeFromStream(ObjectMapper mapper, InputStream stream) throws IOException {
        return  nullIsIllegal((ObjectNode) mapper.readTree(stream), INPUT_JSON_CANNOT_BE_NULL);
    }
}
