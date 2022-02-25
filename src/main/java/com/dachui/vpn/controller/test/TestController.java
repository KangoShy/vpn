package com.dachui.vpn.controller.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dachui.vpn.config.SpringProperties;
import com.dachui.vpn.service.TestService;
import com.dachui.vpn.service.VpnService;
import lombok.Data;
import lombok.SneakyThrows;
import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/test")
@SuppressWarnings("all")
public class TestController {

    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(10, 30, 100, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    @Resource
    private VpnService vpnService;
    @Resource
    private SpringProperties springProperties;
    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/getSties")
    private void testGet() {
        Integer timeout = springProperties.getTimeout();
        System.err.println(timeout);
    }

    @SneakyThrows
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();

        for(;;) {
            if (myThread.isFlag()) {
                System.err.println("主线程访问到flag变量");
                break;
            }
        }
    }


    static class MyThread extends Thread {

        volatile private boolean flag = false;

        @SneakyThrows
        @Override
        public void run() {
            flag = true;
            System.err.println("flag = " + flag);
        }

        public boolean isFlag() {
            return flag;
        }

    }
}
