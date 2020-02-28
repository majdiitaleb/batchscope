package com.batch.batchscope.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@ToString(exclude={"id","contenu"})
public class Erreur {

	private String numLine;
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String contenu;
	private String typeException;
    private String stackTrace;
    
	private String dateCrea;
}
