package com.tanguylegoff.templateapp.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tanguylegoff.templateapp.config.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  /**
   * Default constructor
   *
   * @param authManager The authentication manager
   */
  public JwtAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    // Essayer de récupérer le token depuis l'en-tête Authorization
    String authorization = request.getHeader(Constants.Security.HEADER_AUTHORIZATION);

    // Si l'en-tête Authorization est vide ou ne commence pas par le préfixe attendu, vérifier le cookie
    if (authorization == null || !authorization.startsWith(Constants.Security.JWT_TOKEN_PREFIX)) {
      authorization = getJwtFromCookie(request);
    }

    if (authorization != null) {
      UsernamePasswordAuthenticationToken authentication = getAuthentication(authorization);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    chain.doFilter(request, response);
  }

  private String getJwtFromCookie(HttpServletRequest request) {
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if ("authToken".equals(cookie.getName())) {
          return Constants.Security.JWT_TOKEN_PREFIX + cookie.getValue();
        }
      }
    }
    return null;
  }


  /**
   * Get a representation of the user from the JWT token
   *
   * @param authorization JWT token from the request header
   * @return UsernamePasswordAuthenticationToken
   */
  private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {
    // Oui, c'est mal de mettre la clef en dur.
    // Oui, on devrait utiliser un vault.
    // Non, on n'a pas de vault.
    // Oui le projet n'est pas encore au point où on doit en mettre un.
    // Lorsque cette clef sera importante, je mettrais carrément un certificat signé.
    @SuppressWarnings("java:S6437")
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(Constants.Security.SECRET_KEY.getBytes()))
      .build()
      .verify(authorization.replace(Constants.Security.JWT_TOKEN_PREFIX, ""));

    String subject = decodedJWT.getSubject();
    if (null == subject) {
      return null;
    }

    Long user = Long.valueOf(subject);
    String profile = decodedJWT.getClaim("profile").asString();

    return new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority(profile)));
  }
}