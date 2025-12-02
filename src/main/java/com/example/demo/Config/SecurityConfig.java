package com.example.demo.Config;

import com.example.demo.exceptions.CustomAccessDeniedHandler;
import com.example.demo.exceptions.CustomAuthEntryPoint;
import com.example.demo.jwt.config.JWTFilter;
import com.example.demo.user.service.CustomUserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   DaoAuthenticationProvider authenticationProvider,
                                                   CustomAccessDeniedHandler customAccessDeniedHandler,
                                                   CustomAuthEntryPoint customAuthEntryPoint) throws Exception{


        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth .requestMatchers("/auth/register","/auth/login").permitAll()
                                .requestMatchers("/api/users",
                                        "/api/users/**",
                                        "/api/roles",
                                        "api/posts/*/approve",
                                        "api/posts/*/reject",
                                        "api/posts/*/close").hasRole("ADMIN")
                                .requestMatchers("/api/posts","/api/posts/**").authenticated()
                                .requestMatchers("/api/comments/**").hasAnyRole("USER","ADMIN")
                                .anyRequest().denyAll()

                )
              //  .httpBasic(httpBasic -> httpBasic.disable());

                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                http.exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthEntryPoint));

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailService customUserDetailService){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider(customUserDetailService);
        auth.setPasswordEncoder(bCryptPasswordEncoder());
        return auth;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider(customUserDetailService))
                .build();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

