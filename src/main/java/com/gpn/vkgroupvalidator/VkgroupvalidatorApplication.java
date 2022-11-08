package com.gpn.vkgroupvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VkgroupvalidatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(VkgroupvalidatorApplication.class, args);
    }

}
