package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SastWocProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SastWocProjectApplication.class, args);
    }

}
