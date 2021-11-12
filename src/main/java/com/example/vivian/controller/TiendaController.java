package com.example.vivian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.vivian.service.AppProductoService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/vivian/")
@AllArgsConstructor
public class TiendaController {
	
	private AppProductoService productoService;

	@GetMapping("/")
	public String index(Model model) {
		return "tienda/tienda";
	}
	
	@GetMapping("/producto/{idproducto}")
	public String producto(Model model,@PathVariable(name = "idproducto") Long id) {
		model.addAttribute("producto",productoService.getProducto(id));
		return "tienda/producto";
	}
	
	@PostMapping("/carrito")
	public String carrito(Model model) {
		
		return "tienda/carrito";
	}
	
	@GetMapping("/compras")
	public String compras(Model model) {
		
		return "tienda/compras";
	}
}
