package com.example.demo.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String> {
	
	@Query(value = "SELECT * FROM utente u WHERE u.mail ILIKE :email AND u.password = :pass", nativeQuery = true)
	Utente findUtenteByEmailAndPassword(@Param("email")String email,@Param("pass")String pass);

}
