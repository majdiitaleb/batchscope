package com.batch.batchscope;

import com.batch.batchscope.model.EntrepriseDto;
import com.batch.batchscope.utils.ReaderUtils;
import org.junit.Test;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReaderTest {



    @Test
    public void testReader(){


        PoiItemReader<EntrepriseDto> reader= ReaderUtils.configureExcelItemReader();
        reader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());

        // Testing element in excel reader
        int k=1;
        try{

            EntrepriseDto entr= reader.read();
            while (entr!=null){
                assertNotNull(entr);

                if(k==1) assertTrue(entr.getRs().contains("rs1"));
                if(k==2) assertTrue(entr.getRs().contains("rs2"));
                entr=reader.read();
                k++;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
