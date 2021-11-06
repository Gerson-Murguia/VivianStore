package com.example.vivian.utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class util {

	public static String convertirBase64(String ruta) {
		byte[] bytes;
		try {
			//TODO: arreglar problema 
			bytes = Files.readAllBytes(Paths.get(ruta));
			String base64=Base64.getEncoder().encodeToString(bytes);
			return base64;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: "+e.getMessage());
		}
		return ruta;
	}
}
