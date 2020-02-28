package com.batch.batchscope.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class JobLauncherController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    String fileOutput="outputData.csv";
    String fileinput="entreprise.xlsx";

    @RequestMapping("/lancejob")
    public String handle() throws Exception {
        JobParameters jobParameters=null;
        JobExecution jobExec=null;
        try {
            jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("fileOutput", fileOutput)
                    .addString("inputFile", fileinput)
                    .toJobParameters();
            jobExec = jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }




        return "A '"+ LocalDateTime.now()+"' done job '"+job.getName()+
                "' with params:"+((jobParameters!=null)?jobParameters:"{}")+
                "\njobExec rapport:\n"+((jobExec!=null)?jobExec:"")+
                "\n steps rapport:\n"
                + ((jobExec!=null && jobExec.getStepExecutions()!=null)?jobExec.getStepExecutions():"")
                ;
    }
}
