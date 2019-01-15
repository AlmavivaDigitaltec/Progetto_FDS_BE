package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.view.PrenotabiliViewModel;
import com.example.demo.model.view.PrenotazioniViewModel;
import com.example.demo.model.view.UtenteViewModel;
import com.example.demo.repo.UtenteRepository;
import com.example.demo.Service.PrenotabiliService;
import com.example.demo.Service.PrenotazioniService;
import com.example.demo.Service.UtenteService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("utente")
public class UtenteController {
	
	@Autowired
	UtenteService utenteService;
	@Autowired
	PrenotabiliService prenotabiliService;
	@Autowired
	PrenotazioniService prenotazioniService;
	
	@Autowired
	UtenteRepository utenterepository;
	
	//------------------------------------------GESTIONE DELL'UTENTE-----------------------------
	
	//ACCESSO**********************************************************************************************************
	@PostMapping("/login{uvm}") //FUNZIONA
	public UtenteViewModel login(@RequestBody UtenteViewModel uvm)
	{
		return utenteService.findByMailAndPassword(uvm);
	}
	
	//INSERIMENTO******************************************************************************************************
	@PostMapping("/inserisci/{uvm}")//FUNZIONA
	public String insert(@RequestBody UtenteViewModel uvm) 
	{
		return utenteService.save(uvm);
	}
	
	//MODIFICA******************************************************************************************************
	@PutMapping("/modifica{uvm}") //FUNZIONA
	public String modifica(@RequestBody UtenteViewModel uvm)
	{
		return utenteService.update(uvm);
	}
	
	//CANCELLAZIONE UTENTE DA MATRICOLA*****************************************************************************
	@DeleteMapping("/cancellaUtenteMatricola{uvm}") //FUNZIONA
	public String cancellaDaMatricola(@RequestBody UtenteViewModel uvm)
	{
		return utenteService.deleteByMatricola(uvm);
	}
	
	//VISUALIZZAZIONI***********************************************************************************************
	@GetMapping("/vediUtenteMatricola{uvm}") //FUNZIONA
	public UtenteViewModel findUserByMatricola(UtenteViewModel uvm) 
	{
		return utenteService.findById(uvm);
	}
	
	//------------------------------------GESTIONE DEI PRENOTABILI-----------------------------
	//VEDI TUTTI I PRENOTABILI
	@GetMapping("/vediTuttiPrenotabili") //FUNZIONA
	public List<PrenotabiliViewModel> VediTuttiPrenotabili()
	{
		return prenotabiliService.LoadAllPrenotabili();
	}
	
	
	//-------------------------------------GESTIONE DELLE PRENOTAZIONI------------------------
	@DeleteMapping("/cancellaMiaPrenotazione")
	public String DeleteMyPrenotation(PrenotazioniViewModel pvm)
	{
		return prenotazioniService.deletePrenotazione(pvm);
	}
	
	//INSERIMENTO
	@PostMapping("/inserisciPrenotazione{pvm}") //FUNZIONA
	String insert(@RequestBody PrenotazioniViewModel pvm)
	{
		return prenotazioniService.save(pvm);
	}
	
	//MODIFICA
	@PutMapping("/modificaPrenotazione{pvm}") //FUNZIONA
	String update(@RequestBody PrenotazioniViewModel pvm)
	{	
		return prenotazioniService.updatePrenotazione(
				new PrenotazioniViewModel("0001","203","",null,null,null));
	}
	
	//RICERCA LE PRENOTAZIONI DELL'UTENTE SPECIFICO
	@GetMapping("/vediPrenotazioneUtente{uvm}") //FUNZIONA
	List<PrenotazioniViewModel> getListaPrenotazioniUtente(UtenteViewModel uvm)
	{
		return prenotazioniService.getPrenotazioniUtente(uvm);
	}
	
	//RICERCA LA PRENOTAZIONE
	@GetMapping("/vediPrenotazione{pvm}")//FUNZIONA
	String search(PrenotazioniViewModel pvm)
	{
		return prenotazioniService.findPrenotazione(pvm);
	}
}
