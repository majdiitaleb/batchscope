package com.batch.batchscope.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="contenu")
public class ErreurDto {

	private String numLine;
	private String id;
	private String contenu;
	private String typeException;
    private String stackTrace;
    
	private String dateCrea;
}
