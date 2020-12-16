package com.example.java.restconf;


import javax.ws.rs.core.MediaType;

public class MediaTypeRestconf extends MediaType {

    public static final String APPLICATION_YANG_DATA_XML = "application/yang-data+xml";
    public static final MediaType APPLICATION_YANG_DATA_XML_TYPE = new MediaType("application", "yang-data+xml");
    public static final String APPLICATION_YANG_DATA_JSON = "application/yang-data+json";
    public static final MediaType APPLICATION_YANG_DATA_JSON_TYPE = new MediaType("application", "yang-data+json");

}
