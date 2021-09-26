package com.example.vivian.registro;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistroRequest {
	private final String nombre;
	private final String apellido;
	private final String email;
	private final String password;
}
