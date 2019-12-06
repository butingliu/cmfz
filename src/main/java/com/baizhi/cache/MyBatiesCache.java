package com.baizhi.cache;

import com.baizhi.util.SerializeUtils;
import com.baizhi.util.SpringContextUtil;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MyBatiesCache implements Cache {
    private String id;

    public MyBatiesCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        StringRedisTemplate bean = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        String serialize = SerializeUtils.serialize(value);
        bean.opsForHash().put(id, key.toString(), serialize);
    }

    @Override
    public Object getObject(Object key) {
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        String s = (String) stringRedisTemplate.opsForHash().get(id, key.toString());
        if (s == null) {
            return null;
        }
        Object o = SerializeUtils.serializeToObject(s);
        return o;
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public void clear() {
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        stringRedisTemplate.delete(id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
