package com.batch.batchscope.utils;

import com.batch.batchscope.model.EntrepriseDto;
import com.batch.batchscope.reader.RowMapperImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class ReaderUtils {



    public static final String[] NAMES_COLUMNS = new String[] { "idFourni", "rs", "dateCreation" };



    public static PoiItemReader<EntrepriseDto> configureExcelItemReader()
    {
        System.out.println("testing reader");
        PoiItemReader<EntrepriseDto> reader1= new PoiItemReader<>();
        reader1.setResource(new ClassPathResource("entreprise.xlsx"));

        log.info("row number " +reader1.toString());
        PoiItemReader<EntrepriseDto> reader = new PoiItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("entreprise.xlsx"));
        reader.setRowMapper(new RowMapperImpl());
        return reader;

    }
}
