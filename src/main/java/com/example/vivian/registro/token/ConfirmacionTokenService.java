package com.example.vivian.registro.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

//dependencias se inyectan desde el constructor sin @Autowired desde el spring 4.3
@Service
@AllArgsConstructor
public class ConfirmacionTokenService {
	//gurdar un token de confirmacion
	private final ConfirmacionTokenRepository confirmacionTokenRepository;
	
	public void saveConfirmacionToken(ConfirmacionToken token) {
		confirmacionTokenRepository.save(token);
	};
	
	//obtener el token generado
	public Optional<ConfirmacionToken> getToken(String token){
		return confirmacionTokenRepository.findByToken(token);
	}

	//guardar la confirmacion
	public int setFechaConfirmacion(String token) {
		return confirmacionTokenRepository.updateFechaConfirmacion(token,LocalDateTime.now());
	}
	
}
