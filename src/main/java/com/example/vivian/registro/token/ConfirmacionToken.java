package com.example.vivian.registro.token;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.example.vivian.models.AppUsuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmacionToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String token;
	@Column(nullable = false)
	private LocalDateTime fechaCreado;
	@Column(nullable = false)
	private LocalDateTime fechaExpiracion;
	
	private LocalDateTime fechaConfirmado;
	
	//muchos tokens a 1 usuario
	//podria ser el nombre del id de usuario, en lugar de un nuevo campo
	@ManyToOne
	@JoinColumn(nullable = false,name = "app_user_id")
	private AppUsuario appUsuario;
	
	public ConfirmacionToken(String token, LocalDateTime fechaCreado, LocalDateTime fechaExpiracion,
			AppUsuario appUsuario) {
		this.token = token;
		this.fechaCreado = fechaCreado;
		this.fechaExpiracion = fechaExpiracion;
		this.appUsuario=appUsuario;
	}
	
	
}
