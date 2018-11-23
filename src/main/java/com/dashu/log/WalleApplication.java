package com.dashu.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class WalleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalleApplication.class, args);
    }


}
