package com.example.vivian.registro;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/registro")
@AllArgsConstructor
public class RegistroController {
	//se registra una persona
	
	private RegistroService registroService;
	
	
	@PostMapping
    public String register(@RequestBody RegistroRequest request) {
        //deberia retornar otra cosa como su httpstatus
		//DONE:El correo se envia de forma asincrona con @Async
		return registroService.registrar(request);
    }

	@GetMapping(path = "confirmar")
	public String confirmar(@RequestParam("token") String token) {
		
		//TODO:redirigir a una pagina html con un mensaje, preferentemente vista login
		return registroService.confirmToken(token);
	}
	
}
