package com.example.vivian.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.vivian.models.AppCategoria;
import com.example.vivian.models.AppMarca;
import com.example.vivian.models.AppProducto;
import com.example.vivian.service.AppCategoriaService;
import com.example.vivian.service.AppMarcaService;
import com.example.vivian.service.AppProductoService;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/")
@CrossOrigin("*")
@AllArgsConstructor
public class AdminRestController {

	private AppMarcaService marcaService;
	private AppCategoriaService categoriaService;
	private AppProductoService productoService;
	
	
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
	
	@GetMapping(value = "categoria/{id}")
	public AppCategoria find(@PathVariable Long id) {
		return categoriaService.getCategoria(id);
	}
	
	@PostMapping(path = "/guardarCategoria",produces = "application/json; charset=utf-8")
	public ResponseEntity<AppCategoria> guardarCategoria(@RequestBody AppCategoria appCategoria){
		AppCategoria cat=categoriaService.save(appCategoria);
		return new ResponseEntity<AppCategoria>(cat,HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/eliminarCategoria/{id}",produces = "application/json; charset=utf-8")
	public ResponseEntity<AppCategoria> eliminarCategoria(@PathVariable Long id){
		AppCategoria cat=categoriaService.getCategoria(id);
		if(cat!=null) {
			categoriaService.delete(id);
			return new ResponseEntity<AppCategoria>(HttpStatus.OK);
			}
		else{
			return new ResponseEntity<AppCategoria>(HttpStatus.NO_CONTENT);
		}		
	}
	
	@GetMapping(path = "/listarProducto/{idCategoria}")
	public List<AppProducto> listarProducto(@PathVariable Long idCategoria){
		//TODO el default debe ser 0, o pasarlo manualmente haciendo el pathvariable opcional y comprobar null
		return productoService.listar(idCategoria);
	}
	

	@PostMapping(path = "/guardarProducto",produces = "application/json; charset=utf-8")
	public ResponseEntity<AppProducto> guardarProducto(@RequestPart("imagenArchivo") MultipartFile imagen,@RequestPart("appProducto") String appProducto) throws IOException{
		//TODO: pasar todo al productoservice en lugar de el rest api
		//deserializar json
		System.out.println("json: "+appProducto);
		AppProducto prod=new ObjectMapper().readValue(appProducto, AppProducto.class);
		System.out.println("Objeto deserializado: "+prod);
		//path relativo a imagenes y path absoluto
		String guardarEnRuta="src/main/resources/static/img/productos/";
		String rutaFisica=Paths.get(guardarEnRuta).toAbsolutePath().toString();
		System.out.println("Ruta fisica: " + rutaFisica);
		
		//si el directorio no existe, se crea
		File f=new File(rutaFisica);
		if(!f.isDirectory()) {
			f.mkdirs();
		}else {
			System.out.println("Directorio existe: "+f.getAbsolutePath());
		}
		//TODO: Se crea o se actualiza
		prod=productoService.save(prod);
		
		//reemplaza la ruta de imagen por la imagen especificada
		if(imagen!=null && prod.getIdProducto()!=0) {
			String extension="."+Files.getFileExtension(imagen.getOriginalFilename());
			guardarEnRuta=guardarEnRuta+prod.getIdProducto()+extension;
			
			Path ubicacion=Paths.get(rutaFisica+"/"+prod.getIdProducto()+extension);
			imagen.transferTo(ubicacion);
			
			prod.setRutaImagen(guardarEnRuta);
			productoService.save(prod);
			System.out.println("Imagen guardada con exito");
		}	
		return new ResponseEntity<AppProducto>(prod,HttpStatus.OK);
	}
}
