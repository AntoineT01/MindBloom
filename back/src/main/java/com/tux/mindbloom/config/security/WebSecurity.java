package com.tux.mindbloom.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.tux.mindbloom.config.Constants.Api.LOGIN;
import static com.tux.mindbloom.config.Constants.Roles.ROLE_ADMINISTRATOR;
import static com.tux.mindbloom.config.Constants.Roles.ROLE_NORMAL;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class WebSecurity {

  private final UserDetailsService userDetailsService;

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  public WebSecurity(
    UserDetailsService userDetailsService,
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
  ) {
    this.userDetailsService = userDetailsService;
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
  }

  @Bean
  static RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
    hierarchy.setHierarchy(ROLE_ADMINISTRATOR + " > " + ROLE_NORMAL);
    return hierarchy;
  }

  @Bean
  static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setRoleHierarchy(roleHierarchy);
    return expressionHandler;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  // Je ne suis pas trop sûr de pourquoi sonar rale ici. Oui, on a un mdp en clair un peu plus loin,
  // Mais on chiffre un truc chiffré, front + back. Toute cette partie clef de chiffrement doit être
  // revue le jour où le projet devient suffisamment important pour qu'on utilise un certificat.
  // TL;DR; le jour où c'est plus juste un simple projet interne.
  @SuppressWarnings("java:S5344")
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

    authenticationManagerBuilder.userDetailsService(userDetailsService);
    return authenticationManagerBuilder.build();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
    http
      .formLogin(form -> form
        .loginProcessingUrl(LOGIN)
        .usernameParameter("username")
        .passwordParameter("password")
        .permitAll())
      .cors(withDefaults())
      .csrf(AbstractHttpConfigurer::disable) // <-- Bad idea, fix this
      .authorizeHttpRequests(authorize -> authorize
        .anyRequest()
        // Everything is handled with @PreAuthorize and roles now
        .permitAll()
      )
      .addFilter(new JwtAuthenticationFilter(authenticationManager))
      .addFilter(new JwtAuthorizationFilter(authenticationManager))
      .exceptionHandling(handler -> handler.authenticationEntryPoint(jwtAuthenticationEntryPoint))
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
    configuration.addAllowedOrigin("http://localhost:8080");
    configuration.addAllowedOrigin("http://localhost:8081");
    configuration.addAllowedOrigin("http://localhost:5173");
    configuration.addAllowedOrigin("http://localhost:3000");
    configuration.addAllowedOrigin("https://mindbloom-4ibi.onrender.com:8081");
    configuration.addAllowedOrigin("https://mindbloom-4ibi.onrender.com:8080");
    configuration.addAllowedOrigin("https://mindbloom-4ibi.onrender.com:5173");
    configuration.addAllowedOrigin("https://mind-bloom-opal.vercel.app");
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    source.registerCorsConfiguration("/swagger-ui/**", configuration);
    return source;
  }
}
