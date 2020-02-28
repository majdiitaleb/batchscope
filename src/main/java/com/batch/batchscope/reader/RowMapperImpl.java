package com.batch.batchscope.reader;

import com.batch.batchscope.model.EntrepriseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

import java.sql.Date;

@Slf4j
public class RowMapperImpl implements RowMapper<EntrepriseDto> {
    @Override
    public EntrepriseDto mapRow(RowSet rs) throws Exception {
        log.info("test running , reader excel");


        if (rs == null || rs.getCurrentRow() == null) {
            log.info("rs est null , nothing to show");
            return null;
        }
        EntrepriseDto bl = new EntrepriseDto();
        int number=0;
        try { number = (int)Double.parseDouble(rs.getColumnValue(0)); }
        catch (NumberFormatException nfe) { nfe.printStackTrace(); }


        Date date1=new Date(Long.parseLong(rs.getColumnValue(2)));
         bl.setIdFourni(rs.getColumnValue(0));
        bl.setRs(rs.getColumnValue(1));
        bl.setDateCreation(date1.toString());

        return bl;
    }

}
