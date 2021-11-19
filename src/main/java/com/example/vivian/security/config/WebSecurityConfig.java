package com.example.vivian.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.vivian.service.AppUsuarioService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	private final AppUsuarioService appUsuarioService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final CustomAuthenticationSuccessHandler handler;

	//configura seguridad de http
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
	//permite todo, se puede modificar para mejorar
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login","**/api/v*/**","/css/**","/img/**","/js/**","/registro","/font-awesome-free/**")
			.permitAll()
			.antMatchers("/admin/**").hasAuthority("ADMIN")
			.antMatchers("/vivian/**").hasAnyAuthority("ADMIN","USUARIO")
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login")
			.successHandler(handler).permitAll()
			//.defaultSuccessUrl("/index",true).permitAll()
			.and().logout()
			.logoutUrl("/logout")
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/login");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//el parametro es el bean DaoAuthenticationProvider
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		//se ponen la encriptacion del password y el service
		DaoAuthenticationProvider provider=
				new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(appUsuarioService); 
		return provider;
	}
	
}
/*LOGIN POR DEFECTO
 * <!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Please sign in</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>
  </head>
  <body>
     <div class="container">
      <form class="form-signin" method="post" action="/login">
        <h2 class="form-signin-heading">Please sign in</h2>
        <p>
          <label for="username" class="sr-only">Username</label>
          <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
        </p>
        <p>
          <label for="password" class="sr-only">Password</label>
          <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        </p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
</div>
</body></html>*/
