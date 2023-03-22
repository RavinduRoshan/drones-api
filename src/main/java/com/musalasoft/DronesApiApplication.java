package com.musalasoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.musalasoft", "com.musalasoft"})
@EnableJpaRepositories(basePackages="com.musalasoft.repository")
@EntityScan(basePackages="com.musalasoft.entity")
public class DronesApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DronesApiApplication.class, args);
    }
}
