package com.example.java.netconf;

import com.example.java.JavaApplicationTests;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


class NetconfTranslatorImplTest extends JavaApplicationTests{

    @Autowired
    NetconfTranslator netconfTranslator;


    @Test
    void getDeviceConfig() {
    }
}