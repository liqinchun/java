package com.example.java.controller;

import com.example.java.yangtools.YangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/wifi")
public class WifiController {
    @Autowired
    YangService yangService;

    @PostMapping("123")
    public void config(){

        yangService.addNode(null,null);

    }
}
