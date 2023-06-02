package com.softwaregrouppro.lowcodedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableCaching
@EnableAsync
@SpringBootApplication
public class LowCodeDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(LowCodeDataApplication.class, args);
    }

}
