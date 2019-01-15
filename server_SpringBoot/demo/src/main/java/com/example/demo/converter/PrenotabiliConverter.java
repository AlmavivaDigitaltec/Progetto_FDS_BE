package com.example.demo.converter;

import java.util.List;


public interface PrenotabiliConverter<Prenotabili,PrenotabiliView> {
	
	public Prenotabili fromViewModel(PrenotabiliView pv);
	  
	public List<Prenotabili> fromViewModelList(List<PrenotabiliView> pvmList);
	  
	public PrenotabiliView toViewModel(Prenotabili p);
	  
	public List<PrenotabiliView> toViewModelList(List<Prenotabili> pList);

}
