package com.batch.batchscope.listener;

import com.batch.batchscope.model.Entreprise;
import com.batch.batchscope.model.Erreur;
import com.batch.batchscope.repositories.EntrepriseRepository;
import com.batch.batchscope.repositories.ErreurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class JobCompleteListener extends JobExecutionListenerSupport {

    @Autowired
    EntrepriseRepository entrepRepo;

    @Autowired
    ErreurRepository errRepo;

    @Override public void beforeJob(JobExecution jobExecution){

        List<Entreprise> results = entrepRepo.findAll();
        log.info("==>Found {} nb entreprises in BD before job." , results.size() );

        List<Erreur> resultErrs = errRepo.findAll();
        log.info("==> Found {} nb erreurs in DB.", resultErrs.size());

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            List<Entreprise> results = entrepRepo.findAll();
            log.info("==>{} nb entreprises en BD." , results.size() );
            Collection<StepExecution> stepExecs = jobExecution.getStepExecutions();
            log.debug("==>Rapport {} " ,  stepExecs);
            log.info("==>Liste entreprises portees en BD:" + results + " <==");
            //lignes rejetees
            List<Erreur> resultErrs = errRepo.findAll();
            log.info("==>{} nb erreurs found <==", resultErrs.size());

            log.debug("==>liste erreurs enregistrees en BD:\n{}", resultErrs + " <==");
        }else{
            log.error("==>JOB FAILED"+jobExecution+" <==");
        }
    }
}
