package com.example.demo.model.view;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PrenotabiliViewModel {

	private String codice;
	private String nome;
	private Date data_inizio;
	private Date data_fine;
	private Time ora_inizio;
	private Time ora_fine;
	
	public PrenotabiliViewModel() {}
}
