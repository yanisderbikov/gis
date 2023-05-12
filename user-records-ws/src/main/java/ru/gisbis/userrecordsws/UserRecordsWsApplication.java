package ru.gisbis.userrecordsws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserRecordsWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserRecordsWsApplication.class, args);
    }

}
