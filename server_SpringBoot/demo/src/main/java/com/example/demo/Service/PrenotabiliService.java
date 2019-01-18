package com.example.demo.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.NoSuchElementException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.converter.PrenotabiliConverter;
import com.example.demo.model.Prenotabili;
import com.example.demo.model.view.PrenotabiliViewModel;
import com.example.demo.repo.PrenotabiliRepository;

@Service
public class PrenotabiliService  {

	@Autowired
	PrenotabiliRepository prenotabiliRepository;
	
	@Autowired 
	PrenotabiliConverter<Prenotabili,PrenotabiliViewModel> prenotabiliConverter;
	
	//INSERIMENTO DEL PRENOTABILE
	public PrenotabiliViewModel save(PrenotabiliViewModel uvm1) {
		
		Prenotabili PrenotabileDaInserire = prenotabiliConverter.fromViewModel(uvm1);
	
		try 
		{		
			PrenotabileDaInserire = prenotabiliRepository.findById(PrenotabileDaInserire.getCodice()).get();
			PrenotabileDaInserire =  prenotabiliRepository.save(PrenotabileDaInserire);
			return prenotabiliConverter.toViewModel(PrenotabileDaInserire);
		}
		catch (NoSuchElementException e) 
		{
			return null;
		}
	}

	
	//RESTITUIRE TUTTI I PRENOTABILI
	public List<PrenotabiliViewModel> LoadAllPrenotabili()
	{
		List<Prenotabili> P = new ArrayList<Prenotabili>();
		P = (List<Prenotabili>) prenotabiliRepository.findAll();
		
		if(P.isEmpty())
		{
			List<PrenotabiliViewModel> PvM = new ArrayList<PrenotabiliViewModel>();
			return  PvM;
		}
		else
		{
			return prenotabiliConverter.toViewModelList(P);
		}
	}
	
	//AGGIORNA PRENOTABILE
	public PrenotabiliViewModel UpdatePrenotabile(PrenotabiliViewModel PvM)
	{
		/*VEDO SE IL PRENOTABILE DA MODIFICARE E' REALEMNTE PRESENTE NEL DB*/
		
		Prenotabili PrenotabileDaModificare = prenotabiliConverter.fromViewModel(PvM);
		
		try
		{
			Prenotabili PrenotabilePresente = prenotabiliRepository.findById(PrenotabileDaModificare.getCodice()).get();
			//IN BASE AI CAMPI CHE SONO STATI INSERITI NEL PRENOTABILE DA MODIFICARE..SI PROVVEDE A MODIFICARE QUELLO GIA' PRESENTE
			// NEL DB
		
			/*QUA CI VORREBBERO DEI TRY CATCH*/
			/*PER CAPIRE QUALI CAMPI DEL PRENOTABILE VOGLIO AGGIORNARE,VEDO QUALI,FRA QUELLI DEL PrenotabileDaModificare
			 * NON SONO VUOTI E PROVVEDO AD USARE I LORO VALORI PER MODIFICARE L'EQUIVALENTE PRENOTABILE NEL DB
			 */
			if(!PrenotabileDaModificare.getOrario_inizio().equals(PrenotabilePresente.getOrario_inizio()))
			{
				PrenotabilePresente.setOrario_inizio(PrenotabileDaModificare.getOrario_inizio());
			}
			
			if(!PrenotabileDaModificare.getOrario_fine().equals(PrenotabilePresente.getOrario_fine()))
			{
				PrenotabilePresente.setOrario_fine(PrenotabileDaModificare.getOrario_fine());
			}
			
			if(!PrenotabileDaModificare.getNome().equals(PrenotabilePresente.getNome()))
			{
				PrenotabilePresente.setNome(PrenotabileDaModificare.getNome());
			}
			
			if(PrenotabileDaModificare.getData_inizio().equals(PrenotabilePresente.getData_inizio()))
			{
				PrenotabilePresente.setData_inizio(PrenotabileDaModificare.getData_inizio());
			}
			
			if(PrenotabileDaModificare.getData_fine().equals(PrenotabilePresente.getData_fine()))
			{
				PrenotabilePresente.setData_fine(PrenotabileDaModificare.getData_fine());
			}
			
			if(!PrenotabileDaModificare.getCodice().equals(PrenotabilePresente.getCodice()))
			{
				PrenotabilePresente.setCodice(PrenotabileDaModificare.getCodice());
			}
			
			Prenotabili PrenotabileModificato = prenotabiliRepository.save(PrenotabilePresente);
			return prenotabiliConverter.toViewModel(PrenotabileModificato);
			
		}
		catch(NoSuchElementException e)
		{
			return null;
		}
	}
	
	public PrenotabiliViewModel  DeletePrenotabili(PrenotabiliViewModel PvM)
	{
		Prenotabili PrenotabileDaEliminare = prenotabiliConverter.fromViewModel(PvM);
		try
		{
			prenotabiliRepository.deleteById(PrenotabileDaEliminare.getCodice());
			return PvM;
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
	
}
