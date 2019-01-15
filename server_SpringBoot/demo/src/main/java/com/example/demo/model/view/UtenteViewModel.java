package com.example.demo.model.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor 
@Data
public class UtenteViewModel {
	
	private String matricola;
	private String nome;
	private String cognome;
	private String mail;
	private String password;
	
	public UtenteViewModel() {}
	

}
