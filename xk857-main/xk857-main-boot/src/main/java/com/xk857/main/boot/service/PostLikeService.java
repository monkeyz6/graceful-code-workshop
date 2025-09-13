package com.xk857.main.boot.service;

import com.xk857.main.boot.annotation.WorkshopDemo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.redisson.transaction.TransactionException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@WorkshopDemo(scene = "RTransaction分布式事务", focus = "跨键原子更新")
public class PostLikeService {

    private final RedissonClient redissonClient;

    public void like(String userId, String postId) {
        RTransaction tx = redissonClient.createTransaction(TransactionOptions.defaults());
        RMap<String, Integer> postLikes = tx.getMap("post:likes");
        RSet<String> userLikes = tx.getSet("user:likes:" + userId);
        try {
            Integer current = postLikes.getOrDefault(postId, 0);
            postLikes.put(postId, current + 1);
            userLikes.add(postId);
            tx.commit();
        } catch (TransactionException e) {
            tx.rollback();
            throw e;
        }
    }

    public int likeCount(String postId) {
        return redissonClient.<String, Integer>getMap("post:likes").getOrDefault(postId, 0);
    }

    public boolean userLiked(String userId, String postId) {
        return redissonClient.<String>getSet("user:likes:" + userId).contains(postId);
    }
}

