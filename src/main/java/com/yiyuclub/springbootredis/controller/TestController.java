package com.yiyuclub.springbootredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@RestController
public class TestController {

    @Autowired
    private RedisTemplate redistemplate;

    //    opsForValue： 对应 String（字符串）
//    opsForZSet： 对应 ZSet（有序集合）
//    opsForHash： 对应 Hash（哈希）
//    opsForList： 对应 List（列表）
//    opsForSet： 对应 Set（集合）
    @GetMapping("testredis")
    public void testRedis() {
    }

    /*
    计数器
    dianzan分类，userID区分用户，每次增1
     */
    @GetMapping("rediscount")
    public long redisCount(String userID) {
        Long dianzan = redistemplate.opsForHash().increment("dianzan", userID, 1);
        return dianzan;
    }

    /*
  排行榜
  top自定义排行榜类别，为各用户设定分数
   */
    @GetMapping("redistop")
    public Set redisTop(String top) {
        redistemplate.opsForZSet().add(top, "user1", 70);
        redistemplate.opsForZSet().add(top, "user2", 60);
        redistemplate.opsForZSet().add(top, "user3", 80);
        redistemplate.opsForZSet().add(top, "user4", 90);
        redistemplate.opsForZSet().add(top, "user5", 90);

        Set card = redistemplate.opsForZSet().range(top, 0, -1);//正序
        return card;
    }

    /*
排行榜
time自定义类别，为各用户设定时间戳，根据时间顺序排序
 */
    @GetMapping("redisdate")
    public Set redisDate(String time) {
        time = "redisdate";
        redistemplate.opsForZSet().add(time, "user1", LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        redistemplate.opsForZSet().add(time, "user2", LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        redistemplate.opsForZSet().add(time, "user3", LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        redistemplate.opsForZSet().add(time, "user4", LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        redistemplate.opsForZSet().add(time, "user5", LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());

        Set card = redistemplate.opsForZSet().range(time, 0, -1);//正序
        return card;
    }

    /*
    记录用户操作
    oparate自定义类别，为各userID设定oparate
    */
    @GetMapping("redisoperate")
    public Object redisOperate(String userID, String oparate) {

        redistemplate.opsForHash().put("oparate", userID+"-oparate", oparate);

        Object oparate1 = redistemplate.opsForHash().get("oparate", userID+"-oparate");
        return oparate1;
    }
}
