package com.example.java.mybatis;

import com.example.java.mybatis.entites.User;
import com.example.java.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper mapper;

    public void getById() {
        User user = mapper.getById(new Long(1));
        System.out.println(user);
    }
}
