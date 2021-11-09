package com.example.vivian.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.vivian.models.AppCategoria;
import com.example.vivian.models.AppMarca;
import com.example.vivian.models.AppProducto;
import com.example.vivian.service.AppCategoriaService;
import com.example.vivian.service.AppMarcaService;
import com.example.vivian.service.AppProductoService;
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
	
	/**************************************************************/
	/*********************CRUDPRODUCTOS****************************/
	/**************************************************************/	
	@GetMapping(path = "/listarProducto/{idCategoria}")
	public List<AppProducto> listarProducto(@PathVariable Long idCategoria){
		//TODO el default debe ser 0, o pasarlo manualmente haciendo el pathvariable opcional y comprobar null
		return productoService.listar(idCategoria);
	}
	

	@PostMapping(path = "/guardarProducto",produces = "application/json; charset=utf-8")
	public ResponseEntity<AppProducto> guardarProducto(@RequestPart(name = "imagenArchivo",required = false) MultipartFile imagen,@RequestPart("appProducto") String appProducto) throws IOException{
		//TODO: pasar todo al productoservice en lugar de el rest api
		//deserializar json
		System.out.println("json: "+appProducto);
		AppProducto prod=new ObjectMapper().readValue(appProducto, AppProducto.class);
		System.out.println("Objeto deserializado: "+prod.getIdProducto());
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
		
		if(prod.getIdProducto()==0) {
			System.out.println("El producto se creara");
			prod=productoService.save(prod);
		}else {
			System.out.println("El producto se modificara");
			//modifica todo excepto ruta imagen
			prod=productoService.modificar(prod);
		}
		
		//El producto pasa a tener id diferente a 0, no se guardo la ruta imagen aun
		
		//reemplaza la ruta de imagen por la imagen especificada
		//si recien se crea el producto entra, si se actualiza entra solo si la imagen se ha agregado
		System.out.println(prod.getIdProducto());
		if(!imagen.isEmpty() && prod.getIdProducto()!=0) {
			System.out.println("La imagen es no es nula o el producto no tiene id 0");
			String extension="."+Files.getFileExtension(imagen.getOriginalFilename());
			guardarEnRuta=guardarEnRuta+prod.getIdProducto()+extension;
			
			Path ubicacion=Paths.get(rutaFisica+"/"+prod.getIdProducto()+extension);
			imagen.transferTo(ubicacion);
			productoService.guardarImagen(prod.getIdProducto(),guardarEnRuta);
			System.out.println("Imagen guardada con exito");
		}
		return new ResponseEntity<AppProducto>(prod,HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/eliminarProducto/{id}",produces = "application/json; charset=utf-8")
	public ResponseEntity<AppProducto> eliminarProducto(@PathVariable Long id){
		AppProducto prod=productoService.getProducto(id);
		if(prod!=null) {
			System.out.println("Producto encontrado para eliminarlo");
			productoService.delete(id);
			return new ResponseEntity<AppProducto>(prod,HttpStatus.OK);
			}
		else{
			return new ResponseEntity<AppProducto>(HttpStatus.NO_CONTENT);
		}		
	}
}
