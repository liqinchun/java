package com.example.java.service;

public class ForumServiceImpl implements ForumService{
    public void removeTopic(int topicId){
        System.out.println("模拟删除Topic记录"+topicId);

        try {
            Thread.currentThread().sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void removeFroum(int froumId){
        System.out.println("模拟删除Froum记录"+froumId);

        try {
            Thread.currentThread().sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
