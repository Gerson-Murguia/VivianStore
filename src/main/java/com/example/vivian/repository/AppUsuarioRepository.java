package com.example.vivian.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.vivian.models.AppUsuario;


@Repository
@Transactional(readOnly = true)
//la clase y el tipo del PK
public interface AppUsuarioRepository extends JpaRepository<AppUsuario, Long>{

	
	//metodos a implementar en el APPUSUARIOSERVICE
	Optional<AppUsuario> findByEmail(String email);
	

	@Transactional
	@Modifying
	@Query("UPDATE AppUsuario a "+
	"SET a.enabled=TRUE WHERE a.email=?1")
	int enableAppUsuario(String email);
	

}
