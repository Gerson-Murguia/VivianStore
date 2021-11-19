package com.example.vivian.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.vivian.models.AppCarrito;
import com.example.vivian.repository.AppCarritoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppCarritoService {
	private final AppCarritoRepository carritorepo;
	
	
	public List<AppCarrito> getCarrito(Long idUsuario) {
		List<AppCarrito> carr=carritorepo.findByUsuario_Id(idUsuario);	
		
		return carr;
	}
}
