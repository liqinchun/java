package com.example.java.aop.advice;

import java.sql.SQLException;

public class ForumService {
    public void removeForum(int num) throws Exception {

        throw new Exception("运行时出现异常了");


    }

    public void updateForum(int forum) throws SQLException {
        throw new SQLException("更新数据时出现异常，即将回滚事务。");
    }
}
