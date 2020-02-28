package com.batch.batchscope.common;

import com.batch.batchscope.model.EntrepriseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * This custom {@code ItemWriter} writes the information of the student to
 * the log.
 *
 * @author Petri Kainulainen
 */
public class LoggingStudentWriter implements ItemWriter<EntrepriseDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingStudentWriter.class);

    @Override
    public void write(List<? extends EntrepriseDto> items) throws Exception {
        LOGGER.info("Received the information of {} students", items.size());

        items.forEach(i -> LOGGER.debug("Received the information of a student: {}", i));
    }
}
