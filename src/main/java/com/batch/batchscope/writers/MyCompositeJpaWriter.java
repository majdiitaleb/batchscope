package com.batch.batchscope.writers;

import com.batch.batchscope.model.DynamicEntityPersist;
import com.batch.batchscope.repositories.DynamicEntityPersistRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
@Setter
public class MyCompositeJpaWriter<T extends DynamicEntityPersist> implements ItemWriter<List<T>> {
    @Autowired
   DynamicEntityPersistRepository dynamicEntityPersistRepository;

    @Override
    public void write(List<? extends List<T>> list) throws Exception {

        for (List<T> item: list){
            for(DynamicEntityPersist dynamicEntityPersist:item){
                dynamicEntityPersistRepository.save(dynamicEntityPersist);
                log.info("testing wirting new value " +dynamicEntityPersist.toString());
            }

        }
    }


}
