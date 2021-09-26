package com.example.vivian.email;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;



@Service
public class EmailValidator implements Predicate<String>{

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern patron;
	private Matcher matcher;
	

	
	@Override
	public boolean test(String email) {
		//TODO:Regex para validar el email
		patron=Pattern.compile(EMAIL_PATTERN);
		matcher=patron.matcher(email);
		
		return matcher.matches();
	}

}
