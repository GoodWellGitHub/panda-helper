package com.org;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:/spring.xml")
public class DataBaseDemo {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseDemo.class);

    @Test
    public void test() {
        logger.info("hello");
    }
}
