package com.example.java.mybatis.mapper;

import com.example.java.mybatis.entites.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {


    public User getById(@Param("id") Long id);
}
