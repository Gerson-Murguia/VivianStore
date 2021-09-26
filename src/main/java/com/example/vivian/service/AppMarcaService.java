package com.example.vivian.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.vivian.models.AppMarca;
import com.example.vivian.repository.AppMarcaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppMarcaService {
	
	private final AppMarcaRepository repo;
	
	
	
	public List<AppMarca> listar(){
	
		return repo.findAll();
	}
	
	public void save(AppMarca appMarca) {
	
		repo.save(appMarca);
	}
	
	public AppMarca getMarca(Long id) {
		
		return repo.findById(id).get();
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
}
