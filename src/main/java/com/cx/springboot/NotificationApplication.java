package com.cx.springboot;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author chenxi
 * @Package:
 * @Description:
 * @create 2021·11
 */

@EnableCanalClient
@SpringBootApplication
public class NotificationApplication {
    @PostConstruct
    public void init() {
        // 解决netty启动冲突问题
        // see Netty4Utils.setAvailableProcessors()
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class,args);
    }
}