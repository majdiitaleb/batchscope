package com.batch.batchscope.writers;

import com.batch.batchscope.model.DynamicEntityPersist;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Setter
@Component
public class FlatFileItemsWriter<T extends DynamicEntityPersist>  implements ItemWriter<List<T>>, ItemStream {


    private ItemWriter<DynamicEntityPersist> delegate;
    @Override
    public void write(List<? extends List<T>> list) throws Exception {
        log.info("testing oufff");
        List<DynamicEntityPersist> lists= new ArrayList<>();
        for (List<T> item: list){
            for(DynamicEntityPersist dynamicEntityPersist:item){
                lists.add(dynamicEntityPersist);
                log.info("testing wirting csv " +dynamicEntityPersist.toString());
            }

        }

        delegate.write(lists);
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).open(executionContext);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).update(executionContext);
        }
    }

    @Override
    public void close() throws ItemStreamException {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).close();
        }
    }
}
