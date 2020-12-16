package com.example.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicGrammarTest {

    @Test
    public void switchTest(){
        switch (1){
            case 1:
            case 2:
            case 3:
            case 4:
                System.out.println("4");
                break;
            default:
                System.out.println("default");
        }
    }

    @Test
    public void OptionalTest(){

    }

}
