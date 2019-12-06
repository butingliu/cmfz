package com.baizhi.aspact;

import com.baizhi.entity.MapVo;
import com.baizhi.service.UserService;
import com.google.gson.Gson;
import io.goeasy.GoEasy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Aspect
@Configuration
public class GoEasyAspact {
    @Autowired
    private UserService userService;

    @Around("@annotation(com.baizhi.annotation.GoEasyAnnotation)")
    public Object qq(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object proceed = proceedingJoinPoint.proceed();
        HashMap map = new HashMap();
        Integer day1x = userService.queryUserBySexAndDay("男", 1);
        Integer day1y = userService.queryUserBySexAndDay("女", 1);
        Integer day7x = userService.queryUserBySexAndDay("男", 7);
        Integer day7y = userService.queryUserBySexAndDay("女", 7);
        Integer day30x = userService.queryUserBySexAndDay("男", 30);
        Integer day30y = userService.queryUserBySexAndDay("女", 30);
        Integer day365x = userService.queryUserBySexAndDay("男", 365);
        Integer day365y = userService.queryUserBySexAndDay("女", 365);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(day1x);
        list.add(day7x);
        list.add(day30x);
        list.add(day365x);
        ArrayList<Object> list1 = new ArrayList<>();
        list1.add(day1y);
        list1.add(day7y);
        list1.add(day30y);
        list1.add(day365y);
        List<MapVo> ditu = userService.queryUserBySexAndAddress();
        map.put("man", list);
        map.put("woman", list1);
        map.put("ditu", ditu);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-de6afd1d1d3649e49dc345c422be940b");
        String s = new Gson().toJson(map);
        goEasy.publish("shuijiao", s);
        System.out.println(":运行的goeasy=============");
        return null;
    }
}
