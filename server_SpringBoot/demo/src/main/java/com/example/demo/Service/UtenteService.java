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
	public UtenteViewModel save(UtenteViewModel uvm1) {
	
		Utente u = utenteConverter.fromViewModel(uvm1);
		
		try
		{
			u = utenteRepository.findById(u.getMatricola()).get();
			return null;
		}
		catch(NoSuchElementException e)
		{
			u = utenteRepository.save(u);
			return utenteConverter.toViewModel(u);
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
		
		try
		{
			Utente U = utenteRepository.findById(UVM.getMatricola()).get();		
			return utenteConverter.toViewModel(U);
		} 
		catch(NoSuchElementException e)
		{
			return null;
		}
	}
	
	//RICERCA UTENTE DA EMAIL AND PASSWORD
	public UtenteViewModel findByMailAndPassword(UtenteViewModel UVM) {
		
		try
		{
			Utente U  =  utenteConverter.fromViewModel(UVM);
			U = utenteRepository.findUtenteByEmailAndPassword(U.getMail(),U.getPassword());
			return utenteConverter.toViewModel(U);
		}
		catch(NullPointerException e)
		{
			return null;
		}	
	}
	
	//MODIFICA UTENTE (TUTTI I CAMPI POSSIBILI)
	public UtenteViewModel update(UtenteViewModel UVM) 
	{
		Utente UtenteDaModificare = utenteConverter.fromViewModel(UVM);
		
		try
		{
			Utente UtentePresente = utenteRepository.findById(UtenteDaModificare.getMatricola()).get();
			
			if(!UtenteDaModificare.getNome().equals(UtentePresente.getNome()))
			{
				UtentePresente.setNome(UtenteDaModificare.getNome());
			}
			
			if(!UtenteDaModificare.getCognome().equals(UtentePresente.getCognome()))
			{
				UtentePresente.setCognome(UtenteDaModificare.getCognome());
			}
				
			if(!UtenteDaModificare.getMail().equals(UtentePresente.getMail()))
			{
				UtentePresente.setMail(UtenteDaModificare.getMail());
			}
			
			if(!UtenteDaModificare.getPassword().equals(UtentePresente.getPassword()))
			{
				UtentePresente.setPassword(UtenteDaModificare.getPassword());
			}
				
			Utente UtenteModificato = utenteRepository.save(UtentePresente);
			return utenteConverter.toViewModel(UtenteModificato);
		}
		catch(NoSuchElementException e)
		{
			return null;
		}
	}
	
	//CANCELLA UTENTE DA MATRICOLA
	public UtenteViewModel deleteByMatricola(UtenteViewModel UVM) {
		
		Utente UtenteDaEliminare  =  utenteConverter.fromViewModel(UVM);
		
		try
		{
			utenteRepository.deleteById(UtenteDaEliminare.getMatricola());
			return UVM;
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}		
}
