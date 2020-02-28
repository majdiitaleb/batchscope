package com.batch.batchscope.listener;

import com.batch.batchscope.model.EntrepriseDto;
import com.batch.batchscope.writers.MyErreurJpaWriter;
import com.batch.batchscope.writers.WriterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MyListenerItemProcessor implements ItemProcessListener<EntrepriseDto, List<List<String>>> {
    @Autowired
    MyErreurJpaWriter errWriter;


    @Override
    public void beforeProcess(EntrepriseDto item) {
        log.debug("before item="+item);
    }

    @Override
    public void afterProcess(EntrepriseDto entrepriseDto, List<List<String>> lists) {

    }



    @Override
    public void onProcessError(EntrepriseDto item, Exception e) {

        if( e instanceof ValidationException){
            ValidationException ve = (ValidationException)e;
            log.error("==>Validation Exception in input at line {}. [{}]", item.getNumeroLigne() ,e.getMessage());
            // porter en base les donnees echouees?
            WriterUtils.writeErreurs(item, ve, errWriter);
        }
        if( e instanceof DuplicateKeyException){
            DuplicateKeyException de = (DuplicateKeyException)e;
            log.error("==>Duplication Exception in input at line {}. [{}]", item.getNumeroLigne() ,de.getMessage());
            // porter en base les donnees echouees?
            WriterUtils.writeErreurs(item, de,errWriter);
        }
    }
}
