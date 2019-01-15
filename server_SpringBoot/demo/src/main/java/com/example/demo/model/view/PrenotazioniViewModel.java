package com.example.demo.model.view;

import java.sql.Date;
import java.sql.Time;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrenotazioniViewModel 
{
	private String id_p;	
	private String utente;
	private String prenotabile;	
	private Date data_prenotazione;
	private Time ora_inizio;
	private Time ora_fine;
	
	public PrenotazioniViewModel() {}
}
