package com.example.demo.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converter.PrenotazioneConverter;
import com.example.demo.converter.UtenteConverter;
import com.example.demo.model.Prenotazioni;
import com.example.demo.model.Utente;
import com.example.demo.model.view.PrenotazioniViewModel;
import com.example.demo.model.view.UtenteViewModel;
import com.example.demo.repo.PrenotazioniRepository;

@Service
public class PrenotazioniService {

	@Autowired
	PrenotazioniRepository prenotazioniRepository;
	
	@Autowired
	UtenteConverter<Utente, UtenteViewModel> userConverter;
	
	@Autowired
	PrenotazioneConverter<Prenotazioni,PrenotazioniViewModel> prenotazioneConverter;
	
	static final long Tempo = 1000*60*60*24;
	private Date SYS = new java.sql.Date(Calendar.getInstance().getTime().getTime());
	private Date yesterday = new java.sql.Date(Calendar.getInstance().getTime().getTime() - Tempo);
	
	//##########################################################################INSERIMENTI################################################################################
	
	// INSERIMENTO PRENOTAZIONE
	public PrenotazioniViewModel save(PrenotazioniViewModel pvm1) 
	{		
		Prenotazioni p = prenotazioneConverter.fromViewModel(pvm1);
		
		try
		{
			Prenotazioni P = prenotazioniRepository.findById(p.getId_p()).get();
			return null;
		}
		catch(NoSuchElementException e)
		{
			Date DataIprenotabile = p.getPrenotabile().getData_inizio();
			Date DataFprenotabile = p.getPrenotabile().getData_fine();
			Date DataPrenotaz = p.getData_prenotazione();
			
			Time OraIprenotabile = p.getPrenotabile().getOrario_inizio();
			Time OraFprenotabile = p.getPrenotabile().getOrario_fine();
			Time OraIPrenotaz = p.getOra_inizio();
			Time OraFPrenotaz = p.getOra_fine();
			
			/*QUESTO IF VERIFICA SE L'ORARIO E LA DATA DI PRENOTAZIONI SONO ACCETTABILI PER QUEL PRENOTABILE*/
			if(	(VerificaData(DataIprenotabile,DataFprenotabile,DataPrenotaz)
					&& VerificaOrarioInizio(OraIprenotabile,OraFprenotabile,OraIPrenotaz)
					&& VerificaOrarioFine(OraIprenotabile,OraFprenotabile,OraFPrenotaz)))		
			{
				
				/*QUESTO IF VERIFICA CHE IL PRENOTABILE SCELTO NON SIA GIA' STATO PRENOTATO DA UN ALTRO UTENTE IN QUELL'INTERVALLO
				 * DI TEMPO DI UN GIORNO SPECIFICO
				 */
					
				List<Prenotazioni> P = prenotazioniRepository.ControllaPrenotazione(p.getId_p(),DataPrenotaz,p.getPrenotabile().getCodice(),OraIPrenotaz,OraFPrenotaz);
				
				if(P.isEmpty())
				{
					p = prenotazioniRepository.save(p);
					return pvm1;
				}
				else
				{
					return null;
				}
			}
			else
				return null;
		}
	}
	
	//################################################################### VISUALIZZA PRENOTAZIONI ###############################################################
	
	//RESTITUIRE TUTTI LE PRENOTAZIONI
		public List<PrenotazioniViewModel> LoadAllPrenotazioni()
		{
			List<Prenotazioni> P = new ArrayList<Prenotazioni>();
			P = (List<Prenotazioni>) prenotazioniRepository.findAll();
			
			if(!P.isEmpty())
			{
				return prenotazioneConverter.toViewModelList(P);
			}
			else
			{
				return new ArrayList<PrenotazioniViewModel>();
			}
		}

	//RICERCA SINGOLA PRENOTAZIONE
	public String findPrenotazione(PrenotazioniViewModel pvm) 
	{
		try
		{
			Prenotazioni p = prenotazioniRepository.findById(pvm.getId_p()).get();
			return "Prenotazione: " + prenotazioneConverter.toViewModel(p);
		}
		catch(NoSuchElementException e)
		{
			return "La prenotazione #" + pvm.getId_p() + " non è fra quelle presenti!";
		}
	}	
	
	// RESTITUIRE PRENOTAZIONI UTENTE
		public List<PrenotazioniViewModel> getPrenotazioniUtente(UtenteViewModel uvm)
		{
			Utente u = userConverter.fromViewModel(uvm);
			List<Prenotazioni> miePrenotazioni = prenotazioniRepository.findMyPrenotazioni(u.getMatricola());
			
			if(!miePrenotazioni.isEmpty())
			{
				return prenotazioneConverter.toViewModelList(miePrenotazioni);
			}
			else
				return new ArrayList<PrenotazioniViewModel>();
			
		}
	
	//########################################################### CANCELLAZIONE ########################################################
	
	//CANCELLA SINGOLA PRENOTAZIONE
	public PrenotazioniViewModel deletePrenotazione(PrenotazioniViewModel pvm)
	{
		try
		{
			Prenotazioni prenotazioneDaEliminare = prenotazioneConverter.fromViewModel(pvm);
			Prenotazioni P = prenotazioniRepository.PrenotazioneUtente(pvm.getUtente(),prenotazioneDaEliminare.getId_p());
			prenotazioniRepository.deleteById(P.getId_p());
			return pvm;			
		}
		catch(NullPointerException e)
		{
			return null;
		}			
	}
	
	//CANCELLA DATE PRECEDENTI
	public String deletePrenotazioniObsolete()
	{
		prenotazioniRepository.deletePrenotazioni(SYS);
		return "Prenotazioni precedenti il " + SYS.toString() + " eliminate!";
	}
	
	//######################################################################### AGGIORNAMENTO #####################################################################à
	
	//AGGIORNA PRENOTAZIONE
	public String updatePrenotazione(PrenotazioniViewModel PvM)
	{
		/*VEDO SE LA PRENOTAZIONE DA MODIFICARE E' REALEMNTE PRESENTE NEL DB*/
				
		try
		{
			Prenotazioni PrenotazioneleDaModificare = prenotazioneConverter.fromViewModel(PvM);
			
			Prenotazioni PrenotazionePresente = prenotazioniRepository.PrenotazioneUtente(PvM.getUtente(),PvM.getId_p());
			//Prenotazioni PrenotazionePresente = prenotazioniRepository.findById(PrenotazioneleDaModificare.getId_p()).get();
			
			//IN BASE AI CAMPI CHE SONO STATI INSERITI NELLA PRENOTAZIONE DA MODIFICARE..SI PROVVEDE A MODIFICARE QUELLO GIA' PRESENTE
			// NEL DB
						
			Time OraInizioP = PrenotazionePresente.getOra_inizio();
			Time OraFineP = PrenotazionePresente.getOra_fine();
			String ID_P = PrenotazionePresente.getId_p();
			String CodicePreno = PrenotazionePresente.getPrenotabile().getCodice();
			
			/*PER CAPIRE QUALI CAMPI DEL PRENOTABILE VOGLIO AGGIORNARE,VEDO QUALI,FRA QUELLI DEL PrenotabileDaModificare
			 * NON SONO NULL E PROVVEDO AD USARE I LORO VALORI PER MODIFICARE L'EQUIVALENTE PRENOTABILE NEL DB
			 */
			
						
			if(PrenotazioneleDaModificare.getData_prenotazione() != null)
			{
				Date dataPMod = PrenotazioneleDaModificare.getData_prenotazione();
				
				Date PrenotabileInizio = PrenotazionePresente.getPrenotabile().getData_inizio();
				Date PrenotabileFine = PrenotazionePresente.getPrenotabile().getData_fine();
				
				List<Prenotazioni> P = prenotazioniRepository.ControllaPrenotazione(ID_P,dataPMod,CodicePreno,OraInizioP,OraFineP);
				
				if(P.isEmpty() && VerificaData(PrenotabileInizio,PrenotabileFine,dataPMod))
				{
					PrenotazionePresente.setData_prenotazione(PrenotazioneleDaModificare.getData_prenotazione());
				}
				else
					return "La data viola la prenotazione di un altro utente oppure i vincoli del prenotabile!";
			}
			
			
			if(PrenotazioneleDaModificare.getOra_inizio() != null)
			{
				Time OraInizioPMod = PrenotazioneleDaModificare.getOra_inizio();
				
				Time OraInizio = PrenotazionePresente.getPrenotabile().getOrario_inizio();
				Time OraFine = PrenotazionePresente.getPrenotabile().getOrario_fine();
				
				List<Prenotazioni> P = prenotazioniRepository.ControllaPrenotazione(ID_P,PrenotazionePresente.getData_prenotazione(),
						CodicePreno,OraInizioPMod,OraFineP);
				
				if(P.isEmpty() && VerificaOrarioInizio(OraInizio,OraFine,OraInizioPMod))
				{
					PrenotazionePresente.setOra_inizio(PrenotazioneleDaModificare.getOra_inizio());
				}
				else
					return "L'ora inizio viola la prenotazione di un altro utente oppure i vincoli del prenotabile!";
			}
				
			if(PrenotazioneleDaModificare.getOra_fine() != null)
			{
				Time OraFinePMod = PrenotazioneleDaModificare.getOra_fine();
				
				Time OraInizio = PrenotazionePresente.getPrenotabile().getOrario_inizio();
				Time OraFine = PrenotazionePresente.getPrenotabile().getOrario_fine();
				
				List<Prenotazioni> P = prenotazioniRepository.ControllaPrenotazione(ID_P,PrenotazionePresente.getData_prenotazione(),
						CodicePreno,OraInizioP,OraFinePMod);
				
				if(P.isEmpty() && VerificaOrarioFine(OraInizio,OraFine,OraFinePMod))
				{
					PrenotazionePresente.setOra_fine(PrenotazioneleDaModificare.getOra_fine());
				}
				else
					return "L'ora fine viola la prenotazione di un altro utente oppure i vincoli del prenotabile!";
			}
			
			Prenotazioni PrenotazioneModificata = prenotazioniRepository.save(PrenotazionePresente);
			return "Prenotazione modificata: " + prenotazioneConverter.toViewModel(PrenotazioneModificata);
				
		}
		catch(NullPointerException e)
		{
			return "La prenotazione #" + PvM.getId_p() + " non è relativa all'utente #"+ PvM.getUtente();
		}
	}
		
	
	//###################################################### FUNZIONI DI CONTROLLO ############################################################
	//VERIFICO CHE LA DATA DI PRENOTAZIONI RIENTRI NELL'INTERVALLO DI DISPONIBILITA' DEL PRENOTABILE
	boolean VerificaData(Date DataPreI,Date DataPreF, Date DataPreNuova)
	{
		
		if(DataPreNuova.before(DataPreF) && DataPreNuova.after(DataPreI) && DataPreNuova.after(yesterday) || DataPreNuova.equals(DataPreF))
		{
			return true;
		}
		else
			return false;
	}
	
	//VERIFICO CHE L'ORA INIZIO DELLA PRENOTAZIONI RIENTRI NELL'INTERVALLO DI DISPONIBILITA' DEL PRENOTABILE
	boolean VerificaOrarioInizio(Time OraInizio,Time OraFine,Time OraInizioNuova)
	{
		if(OraInizioNuova.after(OraInizio) && OraInizioNuova.before(OraFine) || OraInizioNuova.equals(OraInizio))
			return true;
		else
			return false;
	}
	
	//VERIFICO CHE L'ORA FINE  DELLA PRENOTAZIONI RIENTRI NELL'INTERVALLO DI DISPONIBILITA' DEL PRENOTABILE
	boolean VerificaOrarioFine(Time OraInizio,Time OraFine,Time OraFineNuova)
	{
		if(OraFineNuova.before(OraFine) && OraFineNuova.after(OraInizio) || OraFineNuova.equals(OraFine))
			return true;
		else
			return false;
	}
}
