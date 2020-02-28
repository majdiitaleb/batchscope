package com.batch.batchscope.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.batch.item.ItemCountAware;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrepriseDto implements ItemCountAware {

    private  int numeroLigne;
    @NotNull(message="Erreur, l'idFourni ne doit etre null!")
    @NotBlank(message="Erreur, l'idFourni '${validatedValue}' ne doit etre vide!")
    @Min(value=1, message="Erreur, l'idFourni '${validatedValue}' doit etre un entier superieur ou egal a {value}")
    private String idFourni;

    private String rs;
    private String dateCreation;

    @Override
    public void setItemCount(int numLine) {
        this.numeroLigne = numLine+1;

    }
}