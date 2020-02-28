package com.batch.batchscope.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.batch.item.ItemCountAware;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
public class TransposeDto  {

    private  int numeroLigne;
       private String idFourni;

    private String rs;
    private String dateCreation;


}