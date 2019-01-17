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
	@GetMapping("/login/{mail}/{password}") //FUNZIONA
	public UtenteViewModel login(@PathVariable String mail, @PathVariable String password)
	{
		return utenteService.findByMailAndPassword(new UtenteViewModel("","","",mail,password));
	}
	
	//INSERIMENTO******************************************************************************************************
	@PostMapping("/inserisci")//FUNZIONA
	public UtenteViewModel insert(@RequestBody UtenteViewModel uvm) 
	{
		return utenteService.save(uvm);
	}
	
	//MODIFICA******************************************************************************************************
	@PutMapping("/modifica") //FUNZIONA
	public UtenteViewModel modifica(@RequestBody UtenteViewModel uvm)
	{
		return utenteService.update(uvm);
	}
	
	//CANCELLAZIONE UTENTE DA MATRICOLA*****************************************************************************
	@DeleteMapping("/cancellaUtenteMatricola/{id}") //FUNZIONA
	public UtenteViewModel cancellaDaMatricola(@PathVariable String id)
	{
		return utenteService.deleteByMatricola(new UtenteViewModel(id,"","","",""));
	}
	
	//VISUALIZZAZIONI***********************************************************************************************
	@GetMapping("/vediUtenteMatricola/{id}") //FUNZIONA
	public UtenteViewModel findUserByMatricola(@PathVariable String id) 
	{
		return utenteService.findById(new UtenteViewModel(id,"","","",""));
	}
	
	//------------------------------------GESTIONE DEI PRENOTABILI-----------------------------
	//VEDI TUTTI I PRENOTABILI
	@GetMapping("/vediTuttiPrenotabili") //FUNZIONA
	public List<PrenotabiliViewModel> VediTuttiPrenotabili()
	{
		return prenotabiliService.LoadAllPrenotabili();
	}
	
	
	//-------------------------------------GESTIONE DELLE PRENOTAZIONI------------------------
	@DeleteMapping("/cancellaMiaPrenotazione/{id}")
	public PrenotazioniViewModel DeleteMyPrenotation(PrenotazioniViewModel pvm)
	{
		return prenotazioniService.deletePrenotazione(pvm);
	}
	
	//INSERIMENTO
	@PostMapping("/inserisciPrenotazione{pvm}") //FUNZIONA
	public PrenotazioniViewModel insert(@RequestBody PrenotazioniViewModel pvm)
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
