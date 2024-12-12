package com.tablero.app.security;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerador {

	//Metodo para crear un token por medio de la authenticacion
	public String generarToken(Authentication authentication) {
		String username = authentication.getName();
		//fecha de emision y expiracion
		Date tiempoActual = new Date();
		Date expiracionToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);
		//Linea para generar el token
		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expiracionToken)
				.signWith(SignatureAlgorithm.HS512, ConstantesSeguridad.JWT_FIRMA)
				.compact();
		return token;
	}
	
	//metodo para extraer un username a partir de un token
	public String obtenerUsernameDeJwt(String token) {
		Claims claims = Jwts.parser() //analizar el token pasado por parametro
					.setSigningKey(ConstantesSeguridad.JWT_FIRMA)
					.parseClaimsJws(token)
					.getBody();
		
		return claims.getSubject();
	}	
	
	//Metodo para validar el token
	public Boolean validarToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(ConstantesSeguridad.JWT_FIRMA).parseClaimsJws(token);
			return true;
		}catch(Exception e) {
			throw new AuthenticationCredentialsNotFoundException("Jwt ha expirado o esta incorrecto");
		}
		
	}
	
	
}
