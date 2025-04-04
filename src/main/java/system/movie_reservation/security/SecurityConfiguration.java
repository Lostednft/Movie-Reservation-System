package system.movie_reservation.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/movies/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/movies").permitAll()
                        .requestMatchers(HttpMethod.GET, "/movies/").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/movies/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/movies", "/movies/").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.POST, "/ticket").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/ticket").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/ticket", "/ticket/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/ticket/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/ticket").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user", "/user/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/user", "/user/").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("Forbidden");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Unauthorized");
                        })

                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
