package com.batch.batchscope;

import com.batch.batchscope.configbatch.AppBatchConfig;
import com.batch.batchscope.repositories.DynamicEntityPersistRepository;
import com.batch.batchscope.repositories.EntrepriseRepository;
import com.batch.batchscope.writers.MyCompositeJpaWriter;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.batch.core.*;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBatchTest
@ContextConfiguration(classes = {  AppBatchConfig.class })
@ComponentScan("com.batch.batchscope")
@EnableJpaRepositories
@Configuration
class BatchscopeApplicationTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;


    @After
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    private static final String TEST_INPUT = "entreprise.xlsx";
    private static final String TEST_OUTPUT = "outputData.csv";
    private static final String EXPECTED_OUTPUT = "outputData.csv";

    DynamicEntityPersistRepository dynamicEntityPersistRepository;




 //   flatFileItemWriters
  private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("file.input", TEST_INPUT);
        paramsBuilder.addString("file.output", TEST_OUTPUT);
        return paramsBuilder.toJobParameters();
    }


    @Test
    public void launchJob() throws Exception {

        //testing a job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

    }
    @Test
    public void checkStatusJobsAndFiles() throws Exception {
        // given
        FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
        FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // then
        assertEquals(actualJobInstance.getJobName(), "myjob");
        assertEquals(actualJobExitStatus.getExitCode(),"COMPLETED");
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }


    @Test
    public void testDatabasePersist()  throws Exception{
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

      assertEquals(dynamicEntityPersistRepository.findAll().size(),3);
    }

    @Test
    void contextLoads() {
    }



 /*
*/
}
