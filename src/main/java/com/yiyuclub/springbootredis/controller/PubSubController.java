package com.yiyuclub.springbootredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PubSubController {

    private static  final String TOP_NAME = "top";

    @Autowired
    public RedisTemplate redisTemplate;

    //发布消息 主题为top
    @GetMapping("send")
    public String redisSend(String messege){
        try {
            redisTemplate.convertAndSend(TOP_NAME,messege);
        } catch (Exception e) {
            return "发送失败";
        }
        return "发送成功";
    }

    public void redisRev(String messege){
        System.out.println(messege);
    }
}
