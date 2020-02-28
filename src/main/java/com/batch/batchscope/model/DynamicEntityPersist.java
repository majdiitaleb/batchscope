package com.batch.batchscope.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
@Entity
public class DynamicEntityPersist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String col1;

    private String col2;

    private String col3;

    private String col4;

    private String col5;


}
