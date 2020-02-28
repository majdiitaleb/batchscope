package com.batch.batchscope;

import com.batch.batchscope.model.TransposeDto;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBatchTest
@ContextConfiguration(classes = {BatchTest.BatchConfiguration.class})
@Configuration
public class BatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;


    @Autowired
    private DataSource dataSource;

    @After
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }


    @Test
    public void launchJob() throws Exception {

        //testing a job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

    }


    @EnableAutoConfiguration
    @EnableBatchProcessing
    @Configuration
    public static class BatchConfiguration {

        @Autowired
        private JobBuilderFactory jobBuilderFactory;

        @Autowired
        private StepBuilderFactory stepBuilderFactory;


        private List<TransposeDto> transposeDtos = new ArrayList<TransposeDto>();


        @Bean
        public ItemReader<TransposeDto> itemReader() {
            transposeDtos.add(new TransposeDto(1, "1", "rs1", "25-02-2020"));
            transposeDtos.add(new TransposeDto(2, "2", "rs2", "24-02-2020"));
            transposeDtos.add(new TransposeDto(1, "3", "rs3", "23-02-2020"));

            return new ListItemReader<>(transposeDtos);
        }

        @Bean
        public ItemWriter<TransposeDto> itemWriter() {
            return (list -> {
                list.forEach(System.out::println);
            });
        }

        @Bean
        public Step step1() {
            return this.stepBuilderFactory.get("step1")
                    .<TransposeDto, TransposeDto>chunk(10)
                    .reader(itemReader())
                    .writer(itemWriter())
                    .build();
        }

        @Bean
        public Job job() {
            return this.jobBuilderFactory.get("job")
                    .start(step1())
                    .build();
        }

        @Bean
        @Profile("test")
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
            dataSource.setUsername("sa");
            dataSource.setPassword("");

            return dataSource;
        }
    }
}
