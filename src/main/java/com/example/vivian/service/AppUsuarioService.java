package com.example.vivian.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.vivian.models.AppUsuario;
import com.example.vivian.registro.token.ConfirmacionToken;
import com.example.vivian.registro.token.ConfirmacionTokenService;
import com.example.vivian.repository.AppUsuarioRepository;

import lombok.AllArgsConstructor;

//para encontrar usuario por el username una vez nos logueamos
@Service
@AllArgsConstructor
//genera un constructor con parametro appUserRepository
public class AppUsuarioService implements UserDetailsService  {

	private final static String USER_NOT_FOUND_MSG="Usuario con email %s no encontrado";
	
	
	private final AppUsuarioRepository appUsuarioRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	//para guardar el token
	private final ConfirmacionTokenService confirmacionTokenService;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		
		return appUsuarioRepository.findByEmail(email)
				.orElseThrow(()->
					new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}
	
	public String registroUsuario(AppUsuario appUsuario) {
		boolean usuarioExiste=appUsuarioRepository
					.findByEmail(appUsuario.getEmail())
					.isPresent();
		if (usuarioExiste) {
			//TODO si el email no esta confirmado, envia email de confirmacion
			throw new IllegalStateException(String.format("Email %s ya registrado", appUsuario.getEmail()));
		}
		
		//codificar la contraseña del usuario
		String encodedPassword= bCryptPasswordEncoder.encode(appUsuario.getPassword());
		appUsuario.setPassword(encodedPassword);
		//guardar al usuario(como enabled=false, hasta que confirme)
		System.out.print(appUsuario.getAuthorities());
		appUsuarioRepository.save(appUsuario);
		
		//VALIDACION DE EMAIL
		
		//token aleatorio
		String token =UUID.randomUUID().toString();
		
		//se crea el token con fecha de creacion, vencimiento y usuario que lo genera
		ConfirmacionToken confirmacionToken=new ConfirmacionToken(
				token,LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(30L),appUsuario);
		//--los minutos pueden venir de una fila de configuracion
		
		//guardando el token
		confirmacionTokenService.saveConfirmacionToken(confirmacionToken);
	
		return token;
	}
	
	//habilitar usuario
	public int enableAppUsuario(String email) {
		return appUsuarioRepository.enableAppUsuario(email);
	}

}
