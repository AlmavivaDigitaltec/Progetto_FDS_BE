package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.PrenotabiliService;
import com.example.demo.Service.PrenotazioniService;
import com.example.demo.Service.UtenteService;
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
	
	@PutMapping("/modifica{uvm}") //FUNZIONA
	public String modifica(@RequestBody UtenteViewModel uvm)
	{
		return utenteService.update(uvm);
	}
	
	//CANCELLAZIONE UTENTE DA MATRICOLA*****************************************************************************
	@DeleteMapping("/cancellaUtenteMatricola{uvm}")//FUNZIONA
	public String cancellaDaMatricola(@RequestBody UtenteViewModel uvm)
	{
		return utenteService.deleteByMatricola(uvm);
	}
	
	//---------------------------------------------GESTIONE DEI PRENOTABILI---------------------------------------------
	
	//INSERIMENTO PRENOTABILE
	@PostMapping("/inserisciPrenotabile{uvm}") //FUNZIONA
	public String inserisci(@RequestBody PrenotabiliViewModel uvm) 
	{
		return prenotabiliService.save(uvm);
	}
	
	//AGGIORNA PRENOTABILE
	@PutMapping("/modificaPrenotabile{pvm}") //FUNZIONA
	public String AggiornaPrenotabile(@RequestBody PrenotabiliViewModel pvm)
	{
		return prenotabiliService.UpdatePrenotabile(pvm);
	}
		
	//ELIMINA PRENOTABILE
	@DeleteMapping("/eliminaPrenotabile{pmv}") //FUNZIONA
	public String EliminaPrenotabile(@RequestBody PrenotabiliViewModel pvm)
	{
		return prenotabiliService.DeletePrenotabili(pvm);
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
