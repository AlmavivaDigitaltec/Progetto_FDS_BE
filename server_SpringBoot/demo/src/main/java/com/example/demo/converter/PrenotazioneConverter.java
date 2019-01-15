package com.example.demo.converter;

import java.util.List;

public interface PrenotazioneConverter<Prenotazione, PrenotazioneView> 
{
	public Prenotazione fromViewModel(PrenotazioneView vm);
	  
	public List<Prenotazione> fromViewModelList(List<PrenotazioneView> vmList);
	  
	public PrenotazioneView toViewModel(Prenotazione m);
	  
	public List<PrenotazioneView> toViewModelList(List<Prenotazione> mList);
}
