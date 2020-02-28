package com.batch.batchscope;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EnableBatchProcessing
public class BatchscopeApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
      //  System.setProperty("spring.datasource.url","jdbc:h2:localhost:9091/mem:testdb");

        SpringApplication.run(BatchscopeApplication.class, args);
    }



}
