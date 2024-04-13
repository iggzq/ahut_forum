package com.forum.article;

import cn.hutool.core.lang.id.NanoId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest {
    @Test
    public void TestNanoID(){
        String s = NanoId.randomNanoId();
        System.out.println(s);
    }
}
