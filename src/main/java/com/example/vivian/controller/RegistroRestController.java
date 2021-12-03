package com.example.vivian.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vivian.registro.RegistroRequest;
import com.example.vivian.registro.RegistroService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/registro")
@AllArgsConstructor
public class RegistroRestController {
	//se registra una persona
	
	private RegistroService registroService;
	
	
	@PostMapping 
    public ResponseEntity<String>  register(@RequestBody RegistroRequest request) {
        //deberia retornar otra cosa como su httpstatus
		//DONE:El correo se envia de forma asincrona con @Async
		System.out.println("email request: "+request.getEmail());
		System.out.println("entro al metodo register");
		String token=registroService.registrar(request);
		
		return new ResponseEntity<String>(token,HttpStatus.OK);
    }

	@GetMapping(path = "/confirmar")
	public String confirmar(@RequestParam("token") String token) {
		
		//TODO:redirigir a una pagina html con un mensaje, preferentemente vista login
		return registroService.confirmToken(token);
	}
	
	@GetMapping(path = "/test")
	public String test() {
		//TODO:redirigir a una pagina html con un mensaje, preferentemente vista login
		return "test pasado";
	}
}
