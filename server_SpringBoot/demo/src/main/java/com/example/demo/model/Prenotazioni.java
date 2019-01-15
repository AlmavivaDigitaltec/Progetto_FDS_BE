package com.example.demo.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "prenotazioni")
@AllArgsConstructor
@Data
public class Prenotazioni implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	protected String id_p;
	
	@OneToOne
	@JoinColumn(name="matricola")
	private Utente utente ;
	
	@OneToOne
	@JoinColumn(name = "codice")
	private Prenotabili prenotabile;
	
	@Column(name = "data_prenotazione")
	private Date data_prenotazione;
	
	@Column(name = "ora_inizio_p")
	private Time ora_inizio;
	
	@Column(name = "ora_fine_p")
	private Time ora_fine;
	
	protected Prenotazioni() {};
	
}
