package com.example.vivian.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.vivian.models.AppProducto;
import com.example.vivian.repository.AppProductoRepository;
import com.example.vivian.utilidades.util;
import com.google.common.io.Files;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppProductoService {
	private final AppProductoRepository prodrepo;
	
	public List<AppProducto> listar(Long idcategoria){
		List<AppProducto> prod=prodrepo.findAll();
		//Cambiar a datos para frontend
		prod.stream().forEach((p)->{
			p.setBase64(util.convertirBase64(p.getRutaImagen()));
			p.setExtension(Files.getFileExtension(p.getRutaImagen()));
		});	
		//devolver solo productos de una categoria dada
		if (idcategoria!=0){
			//filtrar por categoria
			prod=prod.stream().filter(x->x.getIdCategoria()==idcategoria).collect(Collectors.toList());
		}		
		return prod;
	}
	
	public AppProducto save(AppProducto appProducto) {
	//guardar el producto
		return prodrepo.save(appProducto);
	}
	
	public AppProducto getProducto(Long id) {
		
		return prodrepo.findById(id).get();
	}
	
	public void delete(Long id) {
		prodrepo.deleteById(id);
	}
	
	//modifica todo excepto la ruta imagen
	public AppProducto modificar(AppProducto prod) {
		// TODO Auto-generated method stub
		System.out.println(prod.getFechaUpdate());
		AppProducto producto=this.getProducto(prod.getIdProducto());
		System.out.println(producto.getFechaUpdate());
		
		producto.setIdProducto(prod.getIdProducto());
		producto.setDescripcion(prod.getDescripcion());
		producto.setIdCategoria(prod.getIdCategoria());
		producto.setNombre(prod.getNombre());
		producto.setPrecio(prod.getPrecio());
		producto.setStock(prod.getStock());
		producto.setEsActivo(prod.isEsActivo());
		return prodrepo.save(producto);
	}

	public void guardarImagen(Long id, String guardarEnRuta) {
		// TODO Auto-generated method stub
		AppProducto prod=this.getProducto(id);
		prod.setRutaImagen(guardarEnRuta);
		this.save(prod);
	}


	
	
	/*
	public boolean actualizarRutaImagen(AppProducto oProducto) {
		boolean respuesta=true;
		try {
			prodrepo.actualizarImagen(oProducto.getIdProducto(),oProducto.getRutaImagen());
		} catch (Exception e) {
			System.err.println("Catch actualizar ruta imagen"+e.getMessage());
			respuesta=false;
		}
	
		return respuesta;
	}*/
}
