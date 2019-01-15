package com.example.demo.converter;

import java.util.List;

public interface UtenteConverter<Utente, UtenteView> {
	
	public Utente fromViewModel(UtenteView vm);
	  
	public List<Utente> fromViewModelList(List<UtenteView> vmList);
	  
	public UtenteView toViewModel(Utente m);
	  
	public List<UtenteView> toViewModelList(List<Utente> mList);

}
