package com.batch.batchscope.processeur;

import com.batch.batchscope.model.DynamicEntityPersist;
import com.batch.batchscope.model.Entreprise;
import com.batch.batchscope.model.EntrepriseDto;
import com.batch.batchscope.repositories.EntrepriseRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.SpringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Setter
@Component
public class MyValidatingItemProcessor implements ItemProcessor<EntrepriseDto, List<DynamicEntityPersist>>, StepExecutionListener {
    @Autowired
    EntrepriseRepository entRepo;

    @Autowired
    SpringValidator<EntrepriseDto> springValidator;
    DynamicEntityPersist dn1 = new DynamicEntityPersist();
    DynamicEntityPersist dn2 = new DynamicEntityPersist();
    DynamicEntityPersist dn3 = new DynamicEntityPersist();

    StepExecution jobExecution;

    @Override
    public List<DynamicEntityPersist> process(EntrepriseDto item) throws Exception {
        log.info("Processing and transformation data information: {}", item);
        springValidator.validate(item);
        log.info("item valide: " + item);
        ExecutionContext jobContext = this.jobExecution.getExecutionContext();
        HashMap<String, List<String>> transpose = (HashMap<String, List<String>>) jobContext.get("myTranspose");
        int numRow = (int) jobContext.get("numRow");
        if (!transpose.containsKey("col1")) {
            transpose.put("col1", new ArrayList<>(Arrays.asList(item.getIdFourni())));
            transpose.put("col2", new ArrayList<>(Arrays.asList(item.getRs())));
            transpose.put("col3", new ArrayList<>(Arrays.asList(item.getDateCreation())));
        } else {
            transpose.get("col1").add(item.getIdFourni());
            transpose.get("col2").add(item.getRs());
            transpose.get("col3").add(item.getDateCreation());
        }
       int n = transpose.get("col1").size();
        for (int i = 1; i <= n; i++) {
            String name = "setCol" + i;
            setter(name, transpose.get("col1").get(i - 1), dn1);
            setter(name, transpose.get("col2").get(i - 1), dn2);
            setter(name, transpose.get("col3").get(i - 1), dn3);
        }
        jobContext.put("myTranspose", transpose);
        if (numRow == 3)
            return Arrays.asList(dn1, dn2, dn3);
        else {
            jobContext.put("numRow", numRow + 1);
            return null;
        }
    }

    private void isDuplicateInDbByCleFonctionnel(Entreprise entrep) {
        List<Entreprise> list = entRepo.findAll();//TODO temporaire
        for (Entreprise ent : list) {
            if (ent.equals(entrep)) {
                throw new DuplicateKeyException("Error! Duplicate item:" + entrep + " in db!");
            }
        }
    }

    public void setter(String variable, String value, DynamicEntityPersist operationsInstance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method instanceMethod
                = DynamicEntityPersist.class.getMethod(variable, String.class);
        instanceMethod.invoke(operationsInstance, value);

    }

    private Entreprise convertToEntreprise(EntrepriseDto item) {

        Entreprise entrep = new Entreprise();
        int number = 0; // or any appllication default value
        try {
            number = (int) Double.parseDouble(item.getIdFourni());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        entrep.setIdFourni((long) number);
        entrep.setRs(item.getRs());
        entrep.setDateCreation(item.getDateCreation());
        return entrep;
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.jobExecution = stepExecution;
        HashMap<String, List<String>> transpose = new HashMap<String, List<String>>();
        transpose.put("col1", new ArrayList<>());
        transpose.put("col2", new ArrayList<>());
        transpose.put("col3", new ArrayList<>());
        ExecutionContext jobContext = stepExecution.getExecutionContext();
        jobContext.put("myTranspose", transpose);

        jobContext.put("numRow", 0);

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        this.jobExecution = stepExecution;
        ExecutionContext jobContext = stepExecution.getExecutionContext();
        HashMap<String, List<String>> transpose = (HashMap<String, List<String>>) jobContext.get("myTranspose");
        return null;
    }
}
