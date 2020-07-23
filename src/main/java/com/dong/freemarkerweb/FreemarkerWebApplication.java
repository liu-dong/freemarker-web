package com.dong.freemarkerweb;

import com.dong.freemarkerweb.config.CommonsMultipartConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication
public class FreemarkerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreemarkerWebApplication.class, args);
    }

}
