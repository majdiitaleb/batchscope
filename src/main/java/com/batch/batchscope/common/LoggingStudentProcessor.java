package com.batch.batchscope.common;

import com.batch.batchscope.model.EntrepriseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * This custom {@code ItemProcessor} simply writes the information of the
 * processed student to the log and returns the processed object.
 *
 * @author Petri Kainulainen
 */
public class LoggingStudentProcessor implements ItemProcessor<EntrepriseDto, EntrepriseDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingStudentProcessor.class);

    @Override
    public EntrepriseDto process(EntrepriseDto item) throws Exception {
        LOGGER.info("Processing student information: {}", item);
        return item;
    }
}
