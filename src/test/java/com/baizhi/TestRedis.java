package com.baizhi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void qq() {
//        System.out.println("--------------"+stringRedisTemplate);
        stringRedisTemplate.setStringSerializer(new StringRedisSerializer());
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        //stringRedisTemplate.opsForSet().add("0118a3b0-f9cf-456a-9c88-3c2163316c2b","e2cd63c3-c612-4af6-9fd3-c84e62828320");
        Set<String> s = stringRedisTemplate.opsForSet().members("0118a3b0-f9cf-456a-9c88-3c2163316c2b");
        for (String s1 : s) {
            System.out.println(s1);
        }
    }
}
