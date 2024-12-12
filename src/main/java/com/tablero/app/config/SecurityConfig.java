package com.tablero.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tablero.app.security.JwtAuthenticationEntryPoint;
import com.tablero.app.security.JwtAuthenticationFilter;

@Configuration //Le indica al contenedor de spring que esta es una clase de seguridad al momento de arrancar la aplicacion
@EnableWebSecurity //Indica que se activa la seguridad web en nuestra aplicacion y ademas esta sera una clase la cual contendra toda la configuracion referente a la seguridad
public class SecurityConfig {
	
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint) {
		this.jwtAuthenticationEntryPoint = authenticationEntryPoint;
	}
	
	@Bean //Este bean se encarga de verificar la informacion de los usuarios que se loguearan en nuestra api
	AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager(); //Verifica que el usuario que pasemos y los datos esten en la DB
	}
	
	@Bean //Nos encargaremos de encriptar todas nuestras contraseñas
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean //Se encargar de incorporar el filtro de seguridad de json web token que creamos en nuestra clase anterior
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	@Bean // va a establecer una cadena de filtros de seguridad en nuestra aplicacion y es aqui donde determinaremos los permisos segun los roles de usuarios para acceder a nuestra aplicacion
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf->csrf.disable())
			.exceptionHandling(exceptionHandling -> 
            exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
			.sessionManagement(sessionManagement -> 
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize ->
					authorize
						.requestMatchers("/auth/**").permitAll()
						.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults());
			
		
		http.addFilterBefore(jwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}	
}
