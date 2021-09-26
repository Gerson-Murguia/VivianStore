package com.example.vivian.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class TiendaRestController {

	private AppMarcaService marcaService;
	private AppCategoriaService categoriaService;
	
	
	@GetMapping(path = "/listarMarca")
	public List<AppMarca> listarMarca(){
		return marcaService.listar();
	}
	
	@PostMapping(path = "/guardarMarca")
	public void listarMarca(@RequestBody AppMarca appMarca){
		marcaService.save(appMarca);
	}
	
	@GetMapping(path = "/listarCategoria")
	public List<AppCategoria> listarCategoria(){
		return categoriaService.listar();
	}
}
