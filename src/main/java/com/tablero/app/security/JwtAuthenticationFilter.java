package com.tablero.app.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//La funcion de esta clase sera validar la informacion del token y si esto es exitoso estbalecera la autenticacion de un usuario en la solicitud que se esta realizando por ej en postman
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	
	//2 inyecciones de dependencia
	@Autowired
	private CustomUserDetalService customUserDetalService;
	@Autowired
	private JwtGenerador jwtGenerator;

	private String obtenerTokenDeSolicitud(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length()); // la subcadena despues del 7 lugar
		}
		return null;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = obtenerTokenDeSolicitud(request);
		if(StringUtils.hasText(token) && jwtGenerator.validarToken(token)) {
			String username = jwtGenerator.obtenerUsernameDeJwt(token);
			UserDetails userDetails = customUserDetalService.loadUserByUsername(username);
			List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
			
			if (userRoles.contains("ADMIN")||userRoles.contains("USER")) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
						null,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
