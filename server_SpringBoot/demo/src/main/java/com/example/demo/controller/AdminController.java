package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.PrenotabiliService;
import com.example.demo.Service.PrenotazioniService;
import com.example.demo.Service.UtenteService;
import com.example.demo.model.Utente;
import com.example.demo.model.view.PrenotabiliViewModel;
import com.example.demo.model.view.PrenotazioniViewModel;
import com.example.demo.model.view.UtenteViewModel;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("admin")
public class AdminController {

	@Autowired
	UtenteService utenteService;
	
	@Autowired
	PrenotazioniService prenotazioniService;
	
	@Autowired
	PrenotabiliService prenotabiliService;
	
	//------------------------------------GESTIONE DEGLI UTENTI--------------------------------
	
	@GetMapping("/vediTutto")
	public List<UtenteViewModel> findAllUser(){
		return utenteService.findAll();
	}
	
	@PutMapping("/modifica") //FUNZIONA
	public UtenteViewModel modifica(@RequestBody UtenteViewModel uvm)
	{
		return utenteService.update(uvm);
	}
	
	//CANCELLAZIONE UTENTE DA MATRICOLA*****************************************************************************
	@DeleteMapping("/cancellaUtenteMatricola/{matricola}")//FUNZIONA
	public UtenteViewModel cancellaDaMatricola(@PathVariable String matricola)
	{
		System.out.println(matricola);
		return utenteService.deleteByMatricola(
				new UtenteViewModel(matricola,"","","",""));
	}
	
	//---------------------------------------------GESTIONE DEI PRENOTABILI---------------------------------------------
	
	//INSERIMENTO PRENOTABILE
	@PostMapping("/inserisciPrenotabile") //FUNZIONA
	public PrenotabiliViewModel inserisci(@RequestBody PrenotabiliViewModel pvm) 
	{
		return prenotabiliService.save(pvm);
	}
	
	//AGGIORNA PRENOTABILE
	@PutMapping("/modificaPrenotabile") //FUNZIONA
	public PrenotabiliViewModel AggiornaPrenotabile(@RequestBody PrenotabiliViewModel pvm)
	{
		return prenotabiliService.UpdatePrenotabile(pvm);
	}
		
	//ELIMINA PRENOTABILE
	@DeleteMapping("/eliminaPrenotabile/{codice}") //FUNZIONA
	public PrenotabiliViewModel EliminaPrenotabile(@PathVariable String codice)
	{
		return prenotabiliService.DeletePrenotabili(
				new PrenotabiliViewModel(codice,"",null,null,null,null));
	}
	
	//VEDI TUTTI I PRENOTABILI
	@GetMapping("/vediTutti") //FUNZIONA
	public List<PrenotabiliViewModel> VediTuttiPrenotabili()
	{
		return prenotabiliService.LoadAllPrenotabili();
	}
	
	//-----------------------------------------GESTIONE DELLE PRENOTAZIONI------------------------------------------
		
	//RICERCA LISTA
	@GetMapping("/vediTuttePrenotazioni") //FUNZIONA
	List<PrenotazioniViewModel> getListaPrenotazioni()
	{
		return prenotazioniService.LoadAllPrenotazioni();
	}
	
	//ELIMINA OBSOLETI
	@Transactional
	@DeleteMapping("/cancellaObsoleti")//FUNZIONAAAA
	public String cancella()
	{
		return prenotazioniService.deletePrenotazioniObsolete();
	}
		
}
