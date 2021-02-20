package com.example.java;

import com.example.java.mybatis.entites.OperateVrLive;
import com.example.java.mybatis.mapper.OperateVrLiveMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaApplicationTests {

    @Autowired
    OperateVrLiveMapper operateVrLiveMapper;

    @Test
    public void contextLoads() {
        OperateVrLive operateVrLive = new OperateVrLive();

        operateVrLiveMapper.insert(operateVrLive);

    }

}
