package com.example.demo.converter.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.converter.PrenotazioneConverter;
import com.example.demo.model.Prenotabili;
import com.example.demo.model.Prenotazioni;
import com.example.demo.model.Utente;
import com.example.demo.model.view.PrenotazioniViewModel;
import com.example.demo.repo.PrenotabiliRepository;
import com.example.demo.repo.PrenotazioniRepository;
import com.example.demo.repo.UtenteRepository;

@Component
public class PrenotazioneModelViewConverterImpl implements PrenotazioneConverter<Prenotazioni, PrenotazioniViewModel>
{
	
	@Autowired
	PrenotazioniRepository prenotazioniRepository;
	@Autowired
	UtenteRepository utenteRepository;
	@Autowired
	PrenotabiliRepository prenotabiliRepository;
	
	public Prenotazioni fromViewModel(PrenotazioniViewModel vm) 
	{
							
		Utente U = null;
		Prenotabili P = null;
			
		Optional<Prenotazioni> Prenotazione = prenotazioniRepository.findById(vm.getId_p());
		
		if(Prenotazione.isPresent())
		{
			U = prenotazioniRepository.findById(vm.getId_p()).get().getUtente();
			P = prenotazioniRepository.findById(vm.getId_p()).get().getPrenotabile();
		}
		else
		{
			if(!vm.getUtente().isEmpty() && !vm.getPrenotabile().isEmpty())
			{
				
				U = utenteRepository.findById(vm.getUtente()).get();
				P = prenotabiliRepository.findById(vm.getPrenotabile()).get();
			}
			else
				throw new NoSuchElementException();
		}
			
		return new Prenotazioni(vm.getId_p(),U,P,vm.getData_prenotazione(),vm.getOra_inizio(),vm.getOra_fine());
					
	}

	public List<Prenotazioni> fromViewModelList(List<PrenotazioniViewModel> vmList) {
		List<Prenotazioni> p = new ArrayList<Prenotazioni>();
		
		for(PrenotazioniViewModel uvm: vmList ) {
			p.add(fromViewModel(uvm));
		}
		
		return p;
	}

	public PrenotazioniViewModel toViewModel(Prenotazioni m) {
		return new PrenotazioniViewModel(m.getId_p(), m.getUtente().getMatricola(), m.getPrenotabile().getCodice(), m.getData_prenotazione(),
				m.getOra_inizio(), m.getOra_fine());
	}

	public List<PrenotazioniViewModel> toViewModelList(List<Prenotazioni> mList) {
		List<PrenotazioniViewModel> uvm = new ArrayList<PrenotazioniViewModel>();
		
		for(Prenotazioni p: mList ) {
			uvm.add(toViewModel(p));
		}
		
		return uvm;
	}

}
