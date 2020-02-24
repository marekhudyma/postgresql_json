package com.marekhudyma.postgresql.json;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PostgreSqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostgreSqlApplication.class, args);
    }

}
