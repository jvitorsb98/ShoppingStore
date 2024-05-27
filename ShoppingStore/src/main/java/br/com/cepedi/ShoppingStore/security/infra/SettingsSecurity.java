package br.com.cepedi.ShoppingStore.security.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SettingsSecurity {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/auth/login").permitAll();
                    req.requestMatchers("/auth/register").hasRole("ADMIN");
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                    req.requestMatchers("/auth/reset-password/**").permitAll();
                    req.requestMatchers("/auth/activate-account").permitAll();

                    req.requestMatchers(HttpMethod.POST, "/api/v2/brands/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/api/v2/brands/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/api/v2/brands/**").hasRole("ADMIN");

                    req.requestMatchers(HttpMethod.POST, "/api/v2/categories/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/api/v2/categories/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/api/v2/categories/**").hasRole("ADMIN");

                    req.requestMatchers(HttpMethod.POST, "/api/v2/payments/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/api/v2/payments/**").hasRole("ADMIN");

                    req.requestMatchers(HttpMethod.POST, "/api/v2/possible-facets/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/api/v2/possible-facets/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/api/v2/possible-facets/**").hasRole("ADMIN");

                    req.requestMatchers(HttpMethod.POST, "/api/v2/products/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/api/v2/products/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/api/v2/products/**").hasRole("ADMIN");

                    req.requestMatchers(HttpMethod.POST, "/api/v2/productsAttributte/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/api/v2/productsAttributte/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/api/v2/productsAttributte/**").hasRole("ADMIN");

                    req.requestMatchers(HttpMethod.POST, "/api/v2/productsRating/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/api/v2/productsRating/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/api/v2/productsRating/**").hasRole("ADMIN");

                    req.requestMatchers(HttpMethod.GET, "/api/v2/payments/users/{userId}").authenticated();
                    req.requestMatchers(HttpMethod.GET, "/api/v2/payments").hasRole("ADMIN");

                    req.anyRequest().authenticated();

                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
