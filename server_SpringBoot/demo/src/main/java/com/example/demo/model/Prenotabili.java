package com.example.demo.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Table(name = "prenotabili")
@AllArgsConstructor
@Data
public class Prenotabili implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	protected String codice;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "data_inizio")
	private Date data_inizio;
	
	@Column(name = "data_fine")
	private Date data_fine;
	
	@Column(name = "orario_inizio")
    private Time orario_inizio;
	
	@Column(name = "orario_fine")
	private Time orario_fine;
	
	protected Prenotabili() {};
}