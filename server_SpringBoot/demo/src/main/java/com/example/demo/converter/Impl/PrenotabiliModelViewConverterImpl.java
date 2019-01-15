package com.example.demo.converter.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.converter.PrenotabiliConverter;
import com.example.demo.model.Prenotabili;
import com.example.demo.model.view.PrenotabiliViewModel;

@Component
public class PrenotabiliModelViewConverterImpl implements PrenotabiliConverter<Prenotabili, PrenotabiliViewModel> {


	public Prenotabili fromViewModel(PrenotabiliViewModel pv) {
		// TODO Auto-generated method stub
		Prenotabili p = new Prenotabili(pv.getCodice(), pv.getNome(), pv.getData_inizio(), pv.getData_fine(), pv.getOra_inizio(), pv.getOra_fine());
		return p;
	}

	
	public List<Prenotabili> fromViewModelList(List<PrenotabiliViewModel> pvmList) {
		// TODO Auto-generated method stub
		List<Prenotabili> p = new ArrayList<Prenotabili>();

		for(PrenotabiliViewModel uvm: pvmList ) {
			p.add(fromViewModel(uvm));
		}

		return p;
	}

	
	public PrenotabiliViewModel toViewModel(Prenotabili p) {
		// TODO Auto-generated method stub
		PrenotabiliViewModel pvm = new PrenotabiliViewModel(p.getCodice(), p.getNome(), p.getData_inizio(), p.getData_fine(), p.getOrario_inizio(), p.getOrario_fine());
		return pvm;
	}

	
	public List<PrenotabiliViewModel> toViewModelList(List<Prenotabili> pList) {
		// TODO Auto-generated method stub
		List<PrenotabiliViewModel> uvm = new ArrayList<PrenotabiliViewModel>();
		
		for(Prenotabili u: pList ) {
			uvm.add(toViewModel(u));
		}
		
		return uvm;
	}

}
