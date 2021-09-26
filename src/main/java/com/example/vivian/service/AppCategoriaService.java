package com.example.vivian.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.vivian.models.AppCategoria;
import com.example.vivian.repository.AppCategoriaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppCategoriaService {
	private AppCategoriaRepository repo;
	

	public List<AppCategoria> listar(){
	
		return repo.findAll();
	}
	
	public void save(AppCategoria appCategoria) {
	
		repo.save(appCategoria);
	}
	
	public AppCategoria getCategoria(Long id) {
		
		return repo.findById(id).get();
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}
}
