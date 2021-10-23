package com.example.vivian.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vivian.models.AppCategoria;
import com.example.vivian.models.AppMarca;
import com.example.vivian.service.AppCategoriaService;
import com.example.vivian.service.AppMarcaService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/")
@CrossOrigin("*")
@AllArgsConstructor
public class AdminRestController {

	private AppMarcaService marcaService;
	private AppCategoriaService categoriaService;
	
	
	@GetMapping(path = "/listarMarca")
	public List<AppMarca> listarMarca(){
		return marcaService.listar();
	}
	
	@PostMapping(path = "/guardarMarca")
	public void guardarMarca(@RequestBody AppMarca appMarca){
		marcaService.save(appMarca);
	}
	
	@GetMapping(path = "/listarCategoria")
	public List<AppCategoria> listarCategoria(){
		return categoriaService.listar();
	}
	
	@GetMapping(value = "find/{id}")
	public AppCategoria find(@PathVariable Long id) {
		return categoriaService.getCategoria(id);
	}
	
	@PostMapping(path = "/guardarCategoria")
	public ResponseEntity<AppCategoria> guardarCategoria(@RequestBody AppCategoria appCategoria){
		AppCategoria cat=categoriaService.save(appCategoria);
		return new ResponseEntity<AppCategoria>(cat,HttpStatus.OK);
	}
	
	@GetMapping(path = "/eliminarCategoria/{id}")
	public ResponseEntity<AppCategoria> eliminarCategoria(@PathVariable Long id){
		AppCategoria cat=categoriaService.getCategoria(id);
		if(cat!=null) {
			categoriaService.delete(id);}
		else{
			return new ResponseEntity<AppCategoria>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<AppCategoria>(HttpStatus.OK);
	}
}
