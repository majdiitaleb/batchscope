package com.batch.batchscope.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
@Entity
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long idFourni;

    @NotNull
    private String rs;
    private String dateCreation;


}
