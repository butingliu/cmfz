package com.baizhi;

import com.baizhi.util.SmsSample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSend {
    @Test
    public void qq() {
        SmsSample smsSample = new SmsSample();
        smsSample.qq("13193755071", "666666");
    }
}
