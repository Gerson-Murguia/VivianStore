package com.example.vivian.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.vivian.models.AppCarrito;
import com.example.vivian.models.AppProducto;
import com.example.vivian.models.AppUsuario;
import com.example.vivian.service.AppCarritoService;
import com.example.vivian.service.AppProductoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/vivian/")
@CrossOrigin("*")
@AllArgsConstructor
public class TiendaRestController {
	
	private AppProductoService productoService;
	private AppCarritoService carritoService;
	
	@GetMapping(path = "/listarProducto/{idCategoria}")
	public ResponseEntity<List<AppProducto>> listarProducto(@PathVariable(required = false) Long idCategoria){
		//TODO:implementar paginado
		List<AppProducto> productos;
		if (idCategoria==null) {
			idCategoria=0L;
		}		
		productos=productoService.listar(idCategoria);
		if (productos.isEmpty()) {
			return new ResponseEntity<List<AppProducto>>(productos,HttpStatus.NO_CONTENT);

		}else {
			return new ResponseEntity<List<AppProducto>>(productos,HttpStatus.OK);
		}
	}
	
	//TODO:Obtener datos del usuario desde el frontend

	@GetMapping(path = "/obtenerCarrito")
	@ResponseBody
	public List<AppCarrito> obtenerCarrito(Long idUsuario){
		
		List<AppCarrito> carr;
		carr=carritoService.getCarrito(idUsuario);
		
		if (carr.size()!=0) {
			//1.Crea una lista con varios carritos(appCarrito es en realidad los items)
			//2.Los productos los formatea para front end
	
			
		}
		return carr;
	}
	
	
}
