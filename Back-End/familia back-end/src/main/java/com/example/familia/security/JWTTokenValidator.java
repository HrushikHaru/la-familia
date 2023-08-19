package com.example.familia.security;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(SecurityContext.HEADER);
		
		//The Token will be like (Bearer token)
		
		if(jwt != null) {
			try {
				
				jwt =  jwt.substring(7);	//To remove Bearer before the token for validation
				
				SecretKey key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());
				
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				String username = String.valueOf(claims.get("username"));
				
				String authorities=(String) claims.get("authorities");
				
				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null,auths);
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
			}catch(Exception e) {
				throw new BadCredentialsException("invalid token...");	//If someone passes an invalid token 
			}
		}
		
		
		filterChain.doFilter(request, response);
	}

	protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException{
		return req.getServletPath().equals("/signin");				
	}
}
