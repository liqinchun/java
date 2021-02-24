package com.example.java.mybatis;

import com.example.java.JavaApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
public class UserServiceTest extends JavaApplicationTests{

    @Autowired
    UserService userService;
    @Test
    public void getByIdTest() {
        userService.getById();
    }
}