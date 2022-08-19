package com.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }


    public void addToListLeft(String listKey, Object... values) {
        //绑定操做
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //插入数据
        boundValueOperations.leftPushAll(values);
        //设置过时时间
        boundValueOperations.expire(24 * 3600, TimeUnit.SECONDS);
    }


    public void addToListRight(String listKey, Object... values) {
        //绑定操做
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //插入数据
        boundValueOperations.rightPushAll(values);
        //设置过时时间
        boundValueOperations.expire(24 * 3600, TimeUnit.SECONDS);
    }


    public List<Object> rangeList(String listKey, long start, long end) {
        //绑定操做
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //查询数据
        return boundValueOperations.range(start, end);
    }
}
