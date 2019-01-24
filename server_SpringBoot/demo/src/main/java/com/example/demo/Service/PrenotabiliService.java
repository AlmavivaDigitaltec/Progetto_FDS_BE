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
public class PrenotabiliService {

	@Autowired
	PrenotabiliRepository prenotabiliRepository;

	@Autowired
	PrenotabiliConverter<Prenotabili, PrenotabiliViewModel> prenotabiliConverter;

	// INSERIMENTO DEL PRENOTABILE
	public PrenotabiliViewModel save(PrenotabiliViewModel uvm1) {

		Prenotabili PrenotabileDaInserire = prenotabiliConverter.fromViewModel(uvm1);

		try {
			PrenotabileDaInserire = prenotabiliRepository.findById(PrenotabileDaInserire.getCodice()).get();
			System.out.println("TRY");
			return null;

		} catch (NoSuchElementException e) {

			if (PrenotabileDaInserire.getData_inizio().before(PrenotabileDaInserire.getData_fine())
					&& PrenotabileDaInserire.getOrario_inizio().before(PrenotabileDaInserire.getOrario_fine())) {
				PrenotabileDaInserire = prenotabiliRepository.save(PrenotabileDaInserire);
				return prenotabiliConverter.toViewModel(PrenotabileDaInserire);
			} else {
				System.out.println("else");
				return null;
			}

		}
	}

	// RESTITUIRE TUTTI I PRENOTABILI
	public List<PrenotabiliViewModel> LoadAllPrenotabili() {
		List<Prenotabili> P = new ArrayList<Prenotabili>();
		P = (List<Prenotabili>) prenotabiliRepository.findAll();

		if (P.isEmpty()) {
			List<PrenotabiliViewModel> PvM = new ArrayList<PrenotabiliViewModel>();
			return PvM;
		} else {
			return prenotabiliConverter.toViewModelList(P);
		}
	}

	// AGGIORNA PRENOTABILE
	public PrenotabiliViewModel UpdatePrenotabile(PrenotabiliViewModel PvM) {
		/* VEDO SE IL PRENOTABILE DA MODIFICARE E' REALEMNTE PRESENTE NEL DB */

		Prenotabili PrenotabileDaModificare = prenotabiliConverter.fromViewModel(PvM);

		try {
			Prenotabili PrenotabilePresente = prenotabiliRepository.findById(PrenotabileDaModificare.getCodice()).get();
			/*
			 * IN BASE AI CAMPI CHE SONO STATI INSERITI NEL PRENOTABILE DA MODIFICARE..SI
			 * PROVVEDE A MODIFICARE QUELLO GIA' PRESENTE NEL DB. SE I CAMPI DEL
			 * PrenotabilePresente E QUELLO DEL PrenotabileDaModificare NON SONO UGUALI SI
			 * PROVVEDE A MODIFICARE I CAMPI
			 */

			if (!PrenotabileDaModificare.getOrario_inizio().equals(PrenotabilePresente.getOrario_inizio())) {
				// SE L'ORA INIZIO DEL PRENOTABILE DA MODIFICARE VIENE PRIMA DI QUELLO
				// FINALE...ALLORA SI PROVVEDE A SETTARE IL NUOVO CAMPO DELL'ORA INIZIO
				if (PrenotabileDaModificare.getOrario_inizio().before(PrenotabileDaModificare.getOrario_fine())) {
					PrenotabilePresente.setOrario_inizio(PrenotabileDaModificare.getOrario_inizio());
				} else
					return null;
			}

			if (!PrenotabileDaModificare.getOrario_fine().equals(PrenotabilePresente.getOrario_fine())) {
				// SE L'ORA FINE DEL PRENOTABILE DA MODIFICARE VIENE DOPO QUELLA
				// INIZIALE...ALLORA SI PROVVEDE A SETTARE IL NUOVO CAMPO DELL'ORA FINE
				if (PrenotabileDaModificare.getOrario_fine().after(PrenotabileDaModificare.getOrario_inizio())) {
					PrenotabilePresente.setOrario_fine(PrenotabileDaModificare.getOrario_fine());
				} else
					return null;
			}

			if (!PrenotabileDaModificare.getNome().equals(PrenotabilePresente.getNome())) {
				PrenotabilePresente.setNome(PrenotabileDaModificare.getNome());
			}

			if (PrenotabileDaModificare.getData_inizio().equals(PrenotabilePresente.getData_inizio())) {
				// SE LA DATA INIZIO DEL PRENOTABILE DA MODIFICARE VIENE PRIMA DELLA DATA
				// FINE...ALLORA SI PROVVEDE A SETTARE IL NUOVO VALORE DEL CAMPO DATA INIZIO
				if (PrenotabileDaModificare.getData_inizio().before(PrenotabileDaModificare.getData_fine())) {
					PrenotabilePresente.setData_inizio(PrenotabileDaModificare.getData_inizio());
				}
			}

			if (PrenotabileDaModificare.getData_fine().equals(PrenotabilePresente.getData_fine())) {
				// SE LA DATA FINE DEL PRENOTABILE VIENE DOPO DI QUELLA INIZIALE..ALLORA SI PUO'
				// PROCEDERE A SETTARE IL NUOVO VALORE DEL CAMPO DATA FINE
				if (PrenotabileDaModificare.getData_fine().after(PrenotabileDaModificare.getData_inizio())) {
					PrenotabilePresente.setData_fine(PrenotabileDaModificare.getData_fine());
				} else
					return null;
			}

			if (!PrenotabileDaModificare.getCodice().equals(PrenotabilePresente.getCodice())) {
				PrenotabilePresente.setCodice(PrenotabileDaModificare.getCodice());
			}

			Prenotabili PrenotabileModificato = prenotabiliRepository.save(PrenotabilePresente);
			return prenotabiliConverter.toViewModel(PrenotabileModificato);

		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public PrenotabiliViewModel DeletePrenotabili(PrenotabiliViewModel PvM) {
		Prenotabili PrenotabileDaEliminare = prenotabiliConverter.fromViewModel(PvM);
		try {
			prenotabiliRepository.deleteById(PrenotabileDaEliminare.getCodice());
			return PvM;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
