package com.batch.batchscope;

import com.batch.batchscope.model.Entreprise;
import org.springframework.batch.item.ItemReader;

import static java.lang.Math.toIntExact;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommonTestUtils {

    public static void assertEntreprisesReadden(ItemReader<Entreprise> reader) throws Exception {
        int k=1;
        Entreprise entr = reader.read()  ;
        while( entr!=null  ){
            assertNotNull(entr);
            assertEquals(""+k,""+toIntExact(entr.getId()));
            entr = reader.read();
            k++;
        }
    }
}
