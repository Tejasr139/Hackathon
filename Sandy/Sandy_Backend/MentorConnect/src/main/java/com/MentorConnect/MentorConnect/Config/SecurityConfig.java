package com.MentorConnect.MentorConnect.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 http
 .csrf(csrf -> csrf.disable())
 .cors(cors -> {})
 .authorizeHttpRequests(auth -> auth
                 .requestMatchers("/api/auth/**","/api/mentor/mentees").permitAll()
 .anyRequest().authenticated()
 )
 .oauth2ResourceServer(resourceServer -> resourceServer
                 .jwt(Customizer.withDefaults()) // ✅ Recommended usage
 );

 return http.build();
 }

 @Bean
 public CorsConfigurationSource corsConfigurationSource() {
 CorsConfiguration configuration = new CorsConfiguration();
 configuration.setAllowedOrigins(List.of("http://localhost:5173"));
 configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
 configuration.setAllowedHeaders(List.of("*"));
 configuration.setAllowCredentials(true);

 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
 source.registerCorsConfiguration("/**", configuration);
 return source;
 }

 // ✅ JWT Decoder Bean
         @Bean
          public JwtDecoder jwtDecoder() {
              String secret = "FYQmsZe7bIeGrRy8ehAzfF0B5SpIAQgwFF8+7uTeN6Y="; // Use same as in JwtUtil
              byte[] decodeKey = Base64.getDecoder().decode(secret);
              SecretKey key = new SecretKeySpec(decodeKey, 0,decodeKey.length, "HmacSHA256");
              return NimbusJwtDecoder.withSecretKey(key).build();
          }


}
