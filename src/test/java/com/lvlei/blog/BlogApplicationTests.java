package com.lvlei.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void  test2() {
        String s="s.s.d.w.";
        String[] s2=s.split("\\.");
        for(int i=0;i<s2.length;i++){
            System.out.println(">>>>");
            System.out.println(s2[i]);
            System.out.println(">>>>");
        }
    }

}
