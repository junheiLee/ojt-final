package com.ojt_final.office;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeApplication.class, args);
    }

}
