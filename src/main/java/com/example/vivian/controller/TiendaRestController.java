package com.example.vivian.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.vivian.models.AppCarrito;
import com.example.vivian.models.AppCategoria;
import com.example.vivian.models.AppProducto;
import com.example.vivian.models.AppUsuario;
import com.example.vivian.service.AppCarritoService;
import com.example.vivian.service.AppProductoService;
import com.example.vivian.utilidades.util;
import com.google.common.io.Files;

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
		productos=productoService.listarPorCategoria(idCategoria);
		if (productos.isEmpty()) {
			return new ResponseEntity<List<AppProducto>>(productos,HttpStatus.NO_CONTENT);

		}else {
			return new ResponseEntity<List<AppProducto>>(productos,HttpStatus.OK);
		}
	}
	
	
	@PostMapping(path = "/insertarCarrito")
	public ResponseEntity<Integer> insertarCarrito(@RequestBody AppCarrito carrito,Authentication auth) {
		
		//setear al carrito el usuario
		System.out.println("Usuario email:"+ auth.getName());
		int resultado=0;
	
		try {
			AppUsuario currentUser=(AppUsuario) auth.getPrincipal();
			carrito.setUsuario(currentUser);
			carritoService.save(carrito);	
			resultado=1;
		} catch (Exception e) {
			resultado=0;
			System.err.println(e.getMessage());
		}
		return new ResponseEntity<Integer>(resultado,HttpStatus.OK);
	}
	
	@GetMapping(path = "/carritoCantidad")
	public int cantidadCarrito(Authentication auth) {
		
		AppUsuario currentUser=(AppUsuario) auth.getPrincipal();
			
		return carritoService.getCarritoxUsuario(currentUser.getId()).size();
	}
	@GetMapping(path = "/obtenerCarrito")
	public List<AppCarrito> obtenerCarrito(Authentication auth) {
		
		AppUsuario currentUser=(AppUsuario) auth.getPrincipal();
			
		List<AppCarrito> lista=carritoService.getCarritoxUsuario(currentUser.getId());
		
				
		if(lista.size()>0) {
			//adaptar productos con base 64 y extension, necesito 
			lista.forEach((p)->{
					p.getProducto().setBase64(util.convertirBase64(p.getProducto().getRutaImagen()));
					p.getProducto().setExtension(Files.getFileExtension(p.getProducto().getRutaImagen()));
					
			});
		}
		//1.Crea una lista con varios carritos(appCarrito es en realidad los items)
		//2.Los productos los formatea para front end
		return lista;
	}
	
	@DeleteMapping(path = "/eliminarCarrito/{idcarrito}")
	public ResponseEntity<AppCarrito> eliminarCategoria(@PathVariable Long idcarrito){
		System.out.println("metodo eliminarCarrito");

		AppCarrito carr=carritoService.getCarrito(idcarrito);
		
		if(carr!=null) {
			carritoService.delete(idcarrito);
			return new ResponseEntity<AppCarrito>(carr,HttpStatus.OK);
			}
		else{
			
			return new ResponseEntity<AppCarrito>(HttpStatus.NO_CONTENT);
		}		
	}
	
	@PostMapping(path = "/obtenerDepartamento")
	public int obtenerDepartamento() {
		
		
		return 1;
	}
	
	@PostMapping(path = "/obtenerProvincia")
	public void obtenerProvincia() {
		
	}
	
	@PostMapping(path = "/obtenerDistrito")
	public void obtenerDistrito() {
	
	}
}
