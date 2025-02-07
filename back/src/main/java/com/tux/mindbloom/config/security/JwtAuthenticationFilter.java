package com.tux.mindbloom.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tux.mindbloom.api.models.FormLoginDto;
import com.tux.mindbloom.config.Constants;
import com.tux.mindbloom.config.exceptions.JwtAuthenticationException;
import com.tux.mindbloom.utils.PasswordUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  /**
   * The authentication manager
   */
  private final AuthenticationManager authenticationManager;

  /**
   * The environment
   */
//  private final Environment env; // Ajoutez l'Environment de Spring

  /**
   * Default constructor
   *
   * @param authenticationManager The authentication manager
   */
  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
//    this.env = env;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    try {
      // Lire les informations de connexion (email et mot de passe chiffré)
      FormLoginDto user = new ObjectMapper().readValue(request.getInputStream(), FormLoginDto.class);

//      String decryptedPassword = PasswordUtils.decryptPassword(user.getPassword());
      String decryptedPassword = user.getPassword();

      // Créer un token d'authentification avec le mot de passe déchiffré
      Authentication authentication = new UsernamePasswordAuthenticationToken(
        user.getEmail(),
        decryptedPassword,  // Passer le mot de passe déchiffré pour l'authentification
        new ArrayList<>()
      );

      // Authentifier l'utilisateur
      return authenticationManager.authenticate(authentication);
    } catch (IOException e) {
      throw new JwtAuthenticationException("Error during authentication", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
    // Récupération de la clé secrète depuis les variables d'environnement ou un service sécurisé
    // Création du token JWT
    String jwtToken = JWT.create()
      .withSubject(authResult.getName())
      .withClaim("profile", authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null))
      .withExpiresAt(Date.from(ZonedDateTime.now().plus(Constants.Security.AUTHORIZATION_EXPIRATION_TIME, ChronoUnit.MILLIS).toInstant()))
      .sign(Algorithm.HMAC256(Constants.Security.SECRET_KEY.getBytes()));

    // Créer un cookie pour stocker le token JWT
    Cookie authCookie = createCookie(jwtToken);

    // Ajouter le cookie à la réponse HTTP
    response.addCookie(authCookie);
  }

  private Cookie createCookie(String jwtToken) {
    Cookie authCookie = new Cookie("authToken", jwtToken);
    authCookie.setHttpOnly(true); // Empêche l'accès au cookie via JavaScript
    // Assurer que le cookie est sécurisé seulement en production
//    if (isProduction()) {
//      authCookie.setSecure(true); // Seulement pour la production
//    }
    authCookie.setPath("/"); // Rendre le cookie disponible pour l'ensemble du site
    authCookie.setMaxAge((int) Constants.Security.AUTHORIZATION_EXPIRATION_TIME / 1000); // Durée de vie du cookie
    return authCookie;
  }

//  private boolean isProduction() {
//    return env.acceptsProfiles(Profiles.of("prod"));
//  }
}
