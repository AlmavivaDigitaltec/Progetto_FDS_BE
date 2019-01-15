package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.converter.UtenteConverter;
import com.example.demo.model.Utente;
import com.example.demo.repo.UtenteRepository;
import com.example.demo.model.view.*;


@Service
public class UtenteService  {
	
	@Autowired
	UtenteRepository utenteRepository;
	
	@Autowired
	UtenteConverter<Utente,UtenteViewModel> utenteConverter;
	
	//INSERIMENTO SINGOLO UTENTE
	public String save(UtenteViewModel uvm1) {
	
		Utente u = utenteConverter.fromViewModel(uvm1);
		
		try
		{
			Utente U = utenteRepository.findById(u.getMatricola()).get();
			U = utenteRepository.save(u);
			return "Utente registrato: " + utenteConverter.toViewModel(U);
		}
		catch(NoSuchElementException e)
		{
			return "Esiste già un utente con matricola #" + u.getMatricola();
		}
	}
	
	//LISTA DI TUTTI GLI UTENTI REGISTRATI
	public List<UtenteViewModel> findAll(){
		 List<Utente> uList = utenteRepository.findAll();
		 List<UtenteViewModel> uvmList = new ArrayList<UtenteViewModel>();
		 for(Utente u : uList) {
			 uvmList.add(utenteConverter.toViewModel(u));
		 }
	
		 return uvmList;
	}
	
	//RICERCA SINGOLO UTENTE DALLA MATRICOLA
	public UtenteViewModel findById(UtenteViewModel UVM) {
		
		//Utente U  =  utenteConverter.fromViewModel(UVM);
		try
		{
			Utente U2 = utenteRepository.findById(UVM.getMatricola()).get();		
			return utenteConverter.toViewModel(U2);
		} 
		catch(NoSuchElementException e)
		{
			return new UtenteViewModel();
		}
	}
	
	//RICERCA UTENTE DA EMAIL AND PASSWORD
	public UtenteViewModel findByMailAndPassword(UtenteViewModel UVM) {
		
		try
		{
			Utente U  =  utenteConverter.fromViewModel(UVM);
			Utente U2 = utenteRepository.findUtenteByEmailAndPassword(U.getMail(),U.getPassword());
			return utenteConverter.toViewModel(U2);
		}
		catch(NullPointerException e)
		{
			return null;
		}	
	}
	
	//MODIFICA UTENTE (TUTTI I CAMPI POSSIBILI)
	public String update(UtenteViewModel UVM) 
	{
		Utente UtenteDaModificare = utenteConverter.fromViewModel(UVM);
		
		try
		{
			Utente UtentePresente = utenteRepository.findById(UtenteDaModificare.getMatricola()).get();
			
			if(!UtenteDaModificare.getNome().isEmpty())
			{
				UtentePresente.setNome(UtenteDaModificare.getNome());
			}
			
			if(!UtenteDaModificare.getCognome().isEmpty())
			{
				UtentePresente.setCognome(UtenteDaModificare.getCognome());
			}
				
			if(!UtenteDaModificare.getMail().isEmpty())
			{
				UtentePresente.setMail(UtenteDaModificare.getMail());
			}
			
			if(!UtenteDaModificare.getPassword().isEmpty())
			{
				UtentePresente.setPassword(UtenteDaModificare.getPassword());
			}
				
			Utente UtenteModificato = utenteRepository.save(UtentePresente);
			return "Utente registrato: " + utenteConverter.toViewModel(UtenteModificato);
		}
		catch(NoSuchElementException e)
		{
			return "L'utente matricola #" + UtenteDaModificare.getMatricola() + " non è fra quelli registrati!";
		}
	}
	
	//CANCELLA UTENTE DA MATRICOLA
	public String deleteByMatricola(UtenteViewModel UVM) {
		
		Utente UtenteDaEliminare  =  utenteConverter.fromViewModel(UVM);
		
		try
		{
			utenteRepository.deleteById(UtenteDaEliminare.getMatricola());
			return  "Utente eliminato: " + UtenteDaEliminare;
		}
		catch(EmptyResultDataAccessException e)
		{
			return "L'utente da eliminare non è fra quelli registrati!";
		}
	}		
}
