package com.batch.batchscope.configbatch;

import com.batch.batchscope.listener.JobCompleteListener;
import com.batch.batchscope.model.DynamicEntityPersist;
import com.batch.batchscope.model.EntrepriseDto;
import com.batch.batchscope.processeur.MyValidatingItemProcessor;
import com.batch.batchscope.reader.ReaderUtils;
import com.batch.batchscope.writers.FlatFileItemsWriter;
import com.batch.batchscope.writers.MyCompositeJpaWriter;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.validator.SpringValidator;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class AppBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    String pathToFile = "entreps30.csv";
    private static final String PROPERTY_XML_SOURCE_FILE_PATH = "xml.to.database.job.source.file.path";
    private Resource outputResource = new FileSystemResource("outputData.csv");

    Long maxCount2Read = 10L;



    @Bean
    public org.springframework.validation.Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public <T> SpringValidator<T> springValidatorItem() {
        SpringValidator<T> springValidator = new SpringValidator<>();
        springValidator.setValidator(localValidatorFactoryBean());
        return springValidator;
    }

    @Bean
    public ItemProcessor<EntrepriseDto, List<DynamicEntityPersist>> validItemProcessor() {
        return new MyValidatingItemProcessor();
    }

    @Bean
    @StepScope
    public <T> ItemWriter<List<T>> writer() {
        return new MyCompositeJpaWriter();
    }

    @Bean
    @StepScope
    public FlatFileItemsWriter<DynamicEntityPersist> writeFlat(@Value("#{jobParameters['fileOutput']}") String resource) {
        resource = resource != null ? resource : "outputdata.csv";
        FlatFileItemsWriter<DynamicEntityPersist> flatFileItemWriter = new FlatFileItemsWriter<>();
        flatFileItemWriter.setDelegate(new FlatFileItemWriterBuilder<DynamicEntityPersist>()
                .name("entrepriseWriter")
                .resource(new FileSystemResource(resource))
                .delimited()
                .delimiter(";")
                .names(new String[]{"col1",
                        "col2",
                        "col3",
                        "col4"
                })
                .shouldDeleteIfExists(true)
                .build());
        return flatFileItemWriter;

    }



/*    @Bean
    @StepScope
    public FlatFileItemWriter<DynamicEntityPersist> flatFileItemWriters() {
       return new FlatFileItemWriterBuilder<DynamicEntityPersist>()
			.name("entrepriseWriter")
			.resource(new ClassPathResource(resource))
			.delimited()
				.delimiter(";")
			.names(new String[] {"col1",
						"col2",
					"col3",
                    "col4"
				})
			.shouldDeleteIfExists(true)
				.build();
    }*/


    @Bean
    @StepScope
    public PoiItemReader<EntrepriseDto> reader(@Value("#{jobParameters['inputFile']}") String fileInput) {
        return ReaderUtils.configureExcelItemReader(fileInput);
    }

    @Bean
    @StepScope
    public CompositeItemWriter<List<DynamicEntityPersist>> compositeItemWriter() throws Exception {
        CompositeItemWriter<List<DynamicEntityPersist>> compositeItemWriter = new CompositeItemWriter<>();
        List<org.springframework.batch.item.ItemWriter<? super List<DynamicEntityPersist>>> delegates = new ArrayList<>();
        delegates.add(writer());
        delegates.add(writeFlat("null"));
        compositeItemWriter.setDelegates(delegates);
        return compositeItemWriter;
    }




    @Bean
    public Step mystep(
            final ChunkListener chunkListener,
            final ItemProcessListener<EntrepriseDto, List<List<String>>> procListener,
            final ItemReadListener<EntrepriseDto> readItemListener

    ) throws Exception {

        return stepBuilderFactory.get("mystep")
                .<EntrepriseDto, List<DynamicEntityPersist>>chunk(4)
                .faultTolerant()
                .skip(ValidationException.class)
                .skip(FlatFileParseException.class)
                .skipLimit(3) /**seuil au dela le batch doit etre KO!!**/
                .listener(chunkListener)
                .listener(procListener)
                .listener(readItemListener)
                .reader(reader(null))
                .processor(validItemProcessor())
                .writer(compositeItemWriter())
                .build();
    }

    @Bean
    public Job loadEntrepJob(
            final JobCompleteListener completeJobListener,
            final ChunkListener chunkListener,
            final ItemProcessListener<EntrepriseDto, List<List<String>>> procListener,
            final ItemReadListener<EntrepriseDto> readItemListener
    ) throws Exception {
        return jobBuilderFactory.get("myjob")
                .incrementer(new RunIdIncrementer())
                .listener(completeJobListener)
                .flow(mystep(chunkListener, procListener, readItemListener))
                .end()
                .build();
    }



}
