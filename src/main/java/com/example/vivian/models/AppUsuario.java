package com.example.vivian.models;

import java.util.Collection;
import java.util.Collections;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.vivian.security.AppUsuarioRol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUsuario implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// definir propiedades

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nombres;
	@Column(nullable = false)
	private String apellidos;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	private AppUsuarioRol appUsuarioRol;
	private Boolean locked=false;
	//sera enabled cuando confirmen su correo
	private Boolean enabled=false;
	
	@Transient
	private String confirmarPassword;

	// constructor sin id
	public AppUsuario(String nombres, String apellidos, String email, String password, AppUsuarioRol appUsuarioRol) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
		this.appUsuarioRol = appUsuarioRol;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUsuarioRol.name());
		return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {

		return enabled;
	}

}
