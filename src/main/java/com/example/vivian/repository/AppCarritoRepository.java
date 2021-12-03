package com.example.vivian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vivian.models.AppCarrito;

public interface AppCarritoRepository extends JpaRepository<AppCarrito, Long> {
	
	  List<AppCarrito> findByUsuario_Id(Long id);
	  
}
