package com.example.vivian.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.vivian.models.AppCarrito;
import com.example.vivian.repository.AppCarritoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppCarritoService {
	private final AppCarritoRepository carritorepo;
	
	
	public List<AppCarrito> getCarritoxUsuario(Long idUsuario) {
		List<AppCarrito> carr=carritorepo.findByUsuario_Id(idUsuario);	
		
		return carr;
	}
	public AppCarrito getCarrito(Long idcarrito) {
		Optional<AppCarrito> carr=carritorepo.findById(idcarrito);	
		
		if (carr.isPresent()) {
			return carr.get();
		}
		else {
			System.out.println("No se encontro el carrito por id carrito");
			return null;
		}
	}
	public AppCarrito save(AppCarrito carrito) {
		return carritorepo.save(carrito);
	}
	public void delete(Long id) {
		carritorepo.deleteById(id);
	}
}
