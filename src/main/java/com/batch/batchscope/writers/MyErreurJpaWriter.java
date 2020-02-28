package com.batch.batchscope.writers;

import com.batch.batchscope.model.Erreur;
import com.batch.batchscope.repositories.ErreurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MyErreurJpaWriter implements ItemWriter<Erreur> {
    @Autowired
    ErreurRepository erreurRepo;

    @Override
    public void write(final List<? extends Erreur> errs) {
        for (Erreur e : errs) {
            log.info("Writing err : {}",e.toString());
            erreurRepo.save(e);
        }
    }
}
