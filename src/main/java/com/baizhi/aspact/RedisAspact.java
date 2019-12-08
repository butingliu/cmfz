package com.baizhi.aspact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

//再一次
@Aspect
@Configuration
public class RedisAspact {
    @Resource
    private StringRedisTemplate template;

    @Around("@annotation(com.baizhi.annotation.RedisAnnotation)")
    public Object addRedis(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //通过反射获得类名
        String serviceName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        //通过反射获得方法名
        String name = proceedingJoinPoint.getSignature().getName();
        //通过反射获得方法参数
        Object[] args = proceedingJoinPoint.getArgs();
        //拼接方法和类名
        StringBuilder methodName = new StringBuilder(name);
        for (int i = 0; i < args.length; i++) {
            methodName.append(args[i]);
        }

        //对key序列化
        template.setKeySerializer(new StringRedisSerializer());
        //对hash序列化
        template.setHashKeySerializer(new StringRedisSerializer());
        //        序列化值
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //        为了反序列化成功
        ObjectMapper objectMapper = new ObjectMapper();
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //对值序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        Boolean aBoolean = template.opsForHash().hasKey(serviceName, methodName.toString());
        if (aBoolean) {
            //返回缓存
            return template.opsForHash().get(serviceName, methodName.toString());
        }
        //      service 方法的返回值
        Object proceed = proceedingJoinPoint.proceed();
        //添加缓存
        template.opsForHash().put(serviceName, methodName.toString(), proceed);
        return proceed;

       /* MyBatiesCache m = new MyBatiesCache(proceedingJoinPoint.getClass().getName());
        Object proceed = proceedingJoinPoint.proceed();
        m.putObject(proceedingJoinPoint.getSignature(),proceed);
        return proceed;*/
    }

    /*@Around("@annotation(com.baizhi.annotation.RedisAnnotation)")
    public Object deleteRedis(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed = proceedingJoinPoint.proceed();
        MyBatiesCache m = new MyBatiesCache(proceedingJoinPoint.getClass().getName());
        m.clear();
        return proceed;
    }*/
    @Around("@annotation(com.baizhi.annotation.RedisRemoveAnnotation)")
    public Object removeRedis(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object proceed = proceedingJoinPoint.proceed();
        //获得类名
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        System.out.println(className + "999999999999999");
        //查询key是否存在
        Boolean aBoolean = template.hasKey(className);
        System.out.println(aBoolean);
        if (aBoolean) {
            //template.opsForHash().delete(className);
            template.delete(className);
        }

        return proceed;
    }
}
