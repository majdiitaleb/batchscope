package com.batch.batchscope.listener;

import com.batch.batchscope.model.EntrepriseDto;
import com.batch.batchscope.writers.MyErreurJpaWriter;
import com.batch.batchscope.writers.WriterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
public class MyListenerReader implements ItemReadListener<EntrepriseDto> {

    private AtomicInteger nbItemsRead = new AtomicInteger();

    @Autowired
    MyErreurJpaWriter errWriter;

    @Override
    public void beforeRead() {
    }

    @Override
    public void afterRead(EntrepriseDto item) {
        nbItemsRead.incrementAndGet();
        log.debug("after read item:\n" + item+ " nb items read="+nbItemsRead.get());
    }

    @Override
    public void onReadError(Exception ex) {

        if (ex instanceof FlatFileParseException) {

            FlatFileParseException flatFileParseException = (FlatFileParseException) ex;
            log.error("\t==>err on read num line=" + flatFileParseException.getLineNumber() + " input:"
                    + flatFileParseException.getInput());

            // porter en base les lignes impossible a parser?
            try {

                WriterUtils.writeErreurs(flatFileParseException.getInput(), flatFileParseException, errWriter);
            } catch (Exception e) {
                log.warn("exception:" + e, e);
            }
        }

    }
}
