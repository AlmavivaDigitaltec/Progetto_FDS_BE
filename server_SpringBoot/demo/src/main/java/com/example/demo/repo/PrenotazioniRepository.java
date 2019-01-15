package com.example.demo.repo;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Prenotazioni;


@Repository
public interface PrenotazioniRepository extends  JpaRepository<Prenotazioni,String>
{
	
	@Query(value = "SELECT * FROM prenotazioni  p WHERE p.matricola = :matricola", nativeQuery = true)
	List<Prenotazioni> findMyPrenotazioni(@Param("matricola")String matricola);

	@Modifying
	@Query( value = "DELETE FROM prenotazioni p WHERE p.data_prenotazione < :sysdate", nativeQuery = true)
	void deletePrenotazioni(@Param("sysdate")Date SYS);
	
	@Query(value = "SELECT * FROM prenotazioni p WHERE p.data_prenotazione = :dataP AND p.codice = :codicePreno", nativeQuery = true)
	List<Prenotazioni> findPrenotazioniPrenoPerData(@Param("dataP") Date dataP,@Param("codicePreno") String  codicePreno);
	
	@Query(value = "SELECT * FROM prenotazioni p where p.matricola = :matricola AND p.id_p = :id",nativeQuery = true)
	Prenotazioni PrenotazioneUtente(@Param("matricola") String matricola,@Param("id") String id);
	
	@Query (value = "SELECT * FROM prenotazioni p1 where p1.id_p <> :id AND p1.data_prenotazione = :dataP AND p1.codice = :codicePreno"
			+ " AND (  (p1.ora_inizio_p between :OraIP AND :OraFP) OR (p1.ora_fine_p between :OraIP AND :OraFP)"
			+ " OR (p1.ora_inizio_p < :OraIP AND p1.ora_fine_p > :OraFP))", nativeQuery = true)
	List<Prenotazioni> ControllaPrenotazione(@Param("id")String id,@Param("dataP")Date dataP,@Param("codicePreno")String codicePreno,
								@Param("OraIP")Time OraIP,@Param("OraFP")Time OraFP);
}
