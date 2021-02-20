package com.example.java.springcloud.nacos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nacos")
public class NacosController {

    @Autowired
    DiscoveryClient discoveryClient;


    @GetMapping("/ok")
    public String controllerOne() {
        System.out.println(discoveryClient);
        return "ok";
    }
}
