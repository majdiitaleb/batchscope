package com.batch.batchscope.reader;

import com.batch.batchscope.model.EntrepriseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.SuffixRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class ReaderUtils {

    public static  PoiItemReader<EntrepriseDto> configureExcelItemReader(String inputFile)
    {
        inputFile=inputFile!=null?inputFile:"entreprise.xlsx";
        System.out.println("testing reader");
   /*     PoiItemReader<EntrepriseDto> reader1= new PoiItemReader<>();
        reader1.setResource(new ClassPathResource(inputFile));

        log.info("row number " +reader1.toString());*/
        PoiItemReader<EntrepriseDto> reader = new PoiItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource(inputFile));
        reader.setRowMapper(new RowMapperImpl());
        return reader;

    }

    /*public static <T> FlatFileItemReader<EntrepriseDto> configureFtaFileReaderByDelimiterAndQuoteAndSuffixPolicy(
            final String nameFileCsv, final String[] names, final String delimiter, final char quote,
            final String suffix, final int maxItemCountingReader)
    {
        BeanWrapperFieldSetMapper<EntrepriseDto> beanWrapFS= new BeanWrapperFieldSetMapper<>();
        beanWrapFS.setTargetType(EntrepriseDto.class);

        DelimitedLineTokenizer tokenizer= new DelimitedLineTokenizer();
        tokenizer.setNames(names);
        tokenizer.setDelimiter(delimiter);
        tokenizer.setQuoteCharacter(quote);
        tokenizer.setStrict(true);

        DefaultLineMapper<EntrepriseDto> defLineMapper= new DefaultLineMapper<>();
        defLineMapper.setLineTokenizer(tokenizer);
        defLineMapper.setFieldSetMapper(beanWrapFS);

        FlatFileItemReader<EntrepriseDto> reader= new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(nameFileCsv));
        reader.setLineMapper(defLineMapper);
        reader.setLinesToSkip(1);

        if (suffix!=null && !suffix.isEmpty()) {
            SuffixRecordSeparatorPolicy policy = new SuffixRecordSeparatorPolicy();// new CustomRecordSeparatorPolicy();
            policy.setSuffix(suffix);
            reader.setRecordSeparatorPolicy(policy);
        }
        // maxItemCount for all reader who implements AbstractItemCountingItemStreamItemReader
        reader.setMaxItemCount(maxItemCountingReader);
        return reader;

    }*/


}
