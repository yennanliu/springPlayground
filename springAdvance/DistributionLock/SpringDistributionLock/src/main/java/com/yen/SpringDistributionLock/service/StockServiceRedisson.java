package com.yen.SpringDistributionLock.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Redis Lock with client library (Redisson)
 * <p>
 * <p>
 * <p>
 * - https://youtu.be/ynJQouCae4I?si=yAxtOEJ1ZCPHGLpk&t=570
 */
@Service
public class StockServiceRedisson {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    public void deduct() {

        // get lock
        RLock lock = redissonClient.getLock("lock");

        // lock
        lock.lock();

        try {

            // 1) get stock amount
            String stock = stringRedisTemplate.opsForValue().get("stock").toString();

            // 2) check if stock is enough
            if (stock != null && stock.length() != 0) {

                Integer amount = Integer.valueOf(stock);

                // 3) update stock to DB
                if (amount > 0) {

                    stringRedisTemplate.opsForValue().set("stock", String.valueOf(amount - 1));
                }
            }

        } finally {
            // unlock
            lock.unlock();
        }
    }

}
