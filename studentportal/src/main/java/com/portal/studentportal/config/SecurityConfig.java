package com.portal.studentportal.config;

import com.portal.studentportal.security.JWTUtil;
import com.portal.studentportal.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;
    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthFilter jwtAuthFilter1) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter1;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return  http
                //1 disable csrf (no need for stateless JWT REST APIs)
                .csrf(AbstractHttpConfigurer::disable)
                //2 No sessions , making it stateless
                .sessionManagement(s_-> s_.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //3 no authentication required when registering or logging in  (obv) so permit that
                .authorizeHttpRequests(request-> request
                        .requestMatchers("/auth/register", "/auth/login", "/login", "/register", "/dashboard", "/*.html", "/*.css", "/*.js", "/css/**", "/js/**")
                        .permitAll()
                        // to restrict admin access to other than admins
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // rest all needs to authenticated
                        .anyRequest().authenticated())
                        //4 Enable basic Auth
//              .httpBasic(Customizer.withDefaults())
              .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
              // adding the cors to filter chain
              .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }
    @Bean
    // it is the component that actually performs the authentication logic
    public AuthenticationProvider authenticationProvider() {
        // default DaoAuthenticaionProvider is deprecated so we should use parameterized constructor
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        // convert raw password into hashed one
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        //Your MyStudentService - loads user from DB
        return provider;
    }

    @Bean
    //AuthenticationManager is Spring Security's central authentication coordinator. It takes credentials and decides: "Is this user valid?"
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    // CORS :- Cross - Origin  Resource Sharing
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Which origins are allowed to call your backend
        config.setAllowedOrigins(List.of("*"));  // * means everyone
        // which HTTP methods to allow
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // which headers are allowed in the request
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // apply this config to all endpoints
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
