package com.example.vivian.registro.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ConfirmacionTokenRepository extends JpaRepository<ConfirmacionToken,Long>{

	// matching the method signatures in CrudRepository
	
	//spring autgenera lo logica del query con las query supported keywords
	//https://prnt.sc/1srdiqx
	Optional<ConfirmacionToken> findByToken(String token);

	
	//?1 --> valor del primer parametro
	//?2 --> valor del segundo parametro
	@Transactional
	@Modifying
	@Query("UPDATE ConfirmacionToken c " +
	            "SET c.fechaConfirmado = ?2 " +
	            "WHERE c.token = ?1")
	int updateFechaConfirmacion(String token,
	                          LocalDateTime fechaConfirmado);
}
