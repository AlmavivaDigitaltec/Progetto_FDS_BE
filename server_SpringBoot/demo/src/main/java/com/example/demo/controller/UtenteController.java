package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<UtenteViewModel> login(@PathVariable String mail, @PathVariable String password)
	{
		UtenteViewModel U = utenteService.findByMailAndPassword(new UtenteViewModel("","","",mail,password));
		
		System.out.println(U);
		
		if(U != null)
		{
			System.out.println(ResponseEntity.ok(U));
			return ResponseEntity.ok(U);
		}
		else
		{
			System.out.println(ResponseEntity.notFound().build());
			return ResponseEntity.notFound().build();
		}
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
	@DeleteMapping(value="/cancellaUtenteMatricola/{id}") //FUNZIONA
	public UtenteViewModel cancellaDaMatricola(@PathVariable String id)
	{
		//return utenteService.deleteByMatricola(uvm);
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
	@PostMapping("/inserisciPrenotazione") //FUNZIONA
	public PrenotazioniViewModel insert(@RequestBody PrenotazioniViewModel pvm)
	{
		return prenotazioniService.save(pvm);
	}
	
	//MODIFICA
	@PutMapping("/modificaPrenotazione") //FUNZIONA
	public PrenotazioniViewModel update(@RequestBody PrenotazioniViewModel pvm)
	{	
		return prenotazioniService.updatePrenotazione(pvm);
	}
	
	//RICERCA LE PRENOTAZIONI DELL'UTENTE SPECIFICO
	@GetMapping("/vediPrenotazioneUtente/{matricola}") //FUNZIONA
	List<PrenotazioniViewModel> getListaPrenotazioniUtente(@PathVariable String matricola)
	{
		return prenotazioniService.getPrenotazioniUtente(
				new UtenteViewModel(matricola,"","","",""));
	}
	
	//RICERCA LA PRENOTAZIONE
	@GetMapping("/vediPrenotazione/{id}")//FUNZIONA
	public PrenotazioniViewModel search(@PathVariable String id)
	{
		return prenotazioniService.findPrenotazione(
				new PrenotazioniViewModel(id,"","",null,null,null));
	}
}
