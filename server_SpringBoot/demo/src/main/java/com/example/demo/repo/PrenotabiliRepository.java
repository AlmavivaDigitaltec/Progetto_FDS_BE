package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Prenotabili;

@Repository
public interface PrenotabiliRepository extends JpaRepository<Prenotabili, String>  {
		
	
	//PROBABILE METODO DI AGGIORNAMENTO
	/*
	@Query("UPDATE PRENOTABILI SET NOME = nome WHERE CODICE = codice")
	public void UpdatePrenotabiliNomeByCodice(@Param("nome")String Nome,@Param("codice")String codice);
	
	@Query("UPDATE PRENOTABILI SET Ora_Inizio = OI WHERE CODICE = codice")
	public void UpdatePrenotabiliOra_InizioByCodice(@Param("OI")Time OraInizio,@Param("codice")String codice);
	
	@Query("UPDATE PRENOTABILI SET Ora_Fine = OF WHERE CODICE = codice")
	public void UpdatePrenotabiliOra_FineByCodice(@Param("OF")Time OraFine,@Param("codice")String codice);
	
	@Query("UPDATE PRENOTABILI SET Data_Inizio = DI WHERE CODICE = codice")
	public void UpdatePrenotabiliData_InizioByCodice(@Param("DI")Date DataInizio,@Param("codice")String codice);
	
	@Query("UPDATE PRENOTABILI SET Data_Fine = DF WHERE CODICE = codice")
	public void UpdatePrenotabiliData_FineByCodice(@Param("DF")Date DataFine,@Param("codice")String codice);
	
	@Query("UPDATE PRENOTABILI SET Codice = C WHERE CODICE = codice")
	public void UpdatePrenotabiliCodiceByCodice(@Param("C")String codice,@Param("codice")String codice2);
	*/
}
