package com.batch.batchscope.writers;

import com.batch.batchscope.model.Entreprise;
import com.batch.batchscope.model.EntrepriseDto;
import com.batch.batchscope.model.Erreur;
import com.opencsv.CSVWriter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Setter
public class WriterUtils {

	private String fileName;

	private CSVWriter CSVWriter;

	private FileWriter fileWriter;
	private File file;

	public  WriterUtils(String fileName){
		this.fileName=fileName;
	}

	public static void writeErreurs(EntrepriseDto item, Exception e, ItemWriter<Erreur> errWriter) {
		List<Erreur> listErr=null;
		try{
		Erreur err = new Erreur();
		err.setNumLine(""+item.getNumeroLigne());
		err.setContenu("contenu de l'exception "+e.getClass().getName());
		err.setDateCrea( LocalDate.now().toString());
		err.setTypeException(e.getClass().getName());
		//err.setStackTrace(e.getMessage().substring(50));
		listErr= Arrays.asList(err);
		errWriter.write(listErr);
		} catch (Exception ex) {
			log.warn("\texception:{} exec liste err {}",ex.getMessage(),listErr);
		}	 
	}

	
	public static void writeErreurs(String item, FlatFileParseException e, ItemWriter<Erreur> errWriter) {
		List<Erreur> listErr=null;
		try{
		Erreur err = new Erreur();
		err.setNumLine(""+e.getLineNumber());
		err.setContenu("contenu de l'exception "+e.getClass().getName());
		err.setDateCrea( LocalDate.now().toString());
		err.setTypeException(e.getClass().getName());
		//err.setStackTrace(e.getMessage().substring(50));
		listErr= Arrays.asList(err);
		errWriter.write(listErr);
		} catch (Exception ex) {
			log.warn("\texception:{} exec liste err {}",ex.getMessage(),listErr);
		}	 
	}


	public void writeLine(Entreprise line) {
		try {
			if (CSVWriter == null) initWriter();
			String[] lineStr = new String[3];
			lineStr[0] = line.getIdFourni().toString();
			lineStr[1] = line.getRs();
			lineStr[2] = line.getDateCreation();
			CSVWriter.writeNext(lineStr);
		} catch (Exception e) {
			log.error("Error while writing line in file: " + this.fileName);
		}
	}

	private void initWriter() throws Exception {
		if (file == null) {
			file = new File(fileName);

			file.createNewFile();
		}
		if (fileWriter == null) fileWriter = new FileWriter(file, true);
		if (CSVWriter == null) CSVWriter = new CSVWriter(fileWriter);
	}

	public void closeWriter() {
		try {
			CSVWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			log.error("Error while closing writer.");
		}
	}
}
