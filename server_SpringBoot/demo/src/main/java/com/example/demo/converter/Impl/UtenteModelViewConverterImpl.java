package com.example.demo.converter.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.converter.UtenteConverter;
import com.example.demo.model.Utente;
import com.example.demo.model.view.UtenteViewModel;

@Component
public class UtenteModelViewConverterImpl implements UtenteConverter<Utente, UtenteViewModel>{

	
	//DALLA VIEW ALL'UTENTE
	public Utente fromViewModel(UtenteViewModel vm) {
		// TODO Auto-generated method stub
		Utente u = new Utente(vm.getNome(), vm.getCognome(), vm.getMail(), vm.getPassword(), vm.getMatricola());
		return u;
	}

	
	public List<Utente> fromViewModelList(List<UtenteViewModel> vmList) {
		// TODO Auto-generated method stub
		List<Utente> u = new ArrayList<Utente>();
		
		for(UtenteViewModel uvm: vmList ) {
			u.add(fromViewModel(uvm));
		}
		
		return u;
	}

	
	//DALL'UTENTE AL VIEW
	public UtenteViewModel toViewModel(Utente m) {
		// TODO Auto-generated method stub
		UtenteViewModel u = new UtenteViewModel(m.getMatricola(), m.getNome(), m.getCognome(), m.getMail(), m.getPassword());
		return u;
	}
	
	
	public List<UtenteViewModel> toViewModelList(List<Utente> mList) {
		// TODO Auto-generated method stub
		List<UtenteViewModel> uvm = new ArrayList<UtenteViewModel>();
		
		for(Utente u: mList ) {
			uvm.add(toViewModel(u));
		}
		
		return uvm;

	}

}
