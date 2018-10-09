package com.github.fastdfs;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Rambo Yang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class BaseJUnitTest {

    protected void print(Object obj) {
        System.out.println("结果：" + obj.toString());
    }
}
