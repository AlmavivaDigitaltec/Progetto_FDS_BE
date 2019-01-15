package com.example.demo.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "utente")
@Data
public class Utente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name ="matricola")
	protected String matricola;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name = "cognome")
	private String cognome;
	
	@Column(name = "mail")
	private String mail;
	
	@Column(name = "password")
	private String password;
	
	protected Utente() {};
	
	public Utente(String N,String C,String E,String P,String M)
	  {
	    E.toLowerCase();
	    if(!E.contains("@almaviva.it"))
	    {
	      
	      if(E.contains("@"))
	        E = E.substring(0, E.indexOf("@"));
	      
	      E += "@almaviva.it";
	    }
	    
	    nome = N;
	    cognome = C;
	    mail = E;
	    password = P;
	    matricola= M;
	  }
	
	public void setEmail(String email) 
	{
		email.toLowerCase();
		if(!email.contains("@almaviva.it"))
		{
			
			if(email.contains("@"))
				email = email.substring(0, email.indexOf("@"));
			
			System.out.println(email);
			email += "@almaviva.it";
		}
		
		this.mail = email;
	}
	
	
}
