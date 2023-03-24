package com.example.cloneburgerking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CloneburgerkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloneburgerkingApplication.class, args);
    }

}
