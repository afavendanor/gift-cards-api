package co.com.project.infraestructure.security.config;

import co.com.project.infraestructure.security.filter.JwtAuthenticationFilter;
import co.com.project.infraestructure.security.filter.JwtValidationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(authz -> authz
                 .requestMatchers(HttpMethod.GET, "/health").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/create").hasRole(RoleEnum.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/api/gift-cards/**").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.USER.name())
                .requestMatchers(HttpMethod.POST, "/api/gift-cards/update").hasRole(RoleEnum.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/gift-cards/redeem").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.USER.name())
                .requestMatchers(HttpMethod.PUT, "/api/gift-cards/**").hasRole(RoleEnum.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/gift-cards/**").hasRole(RoleEnum.ADMIN.name())
                .anyRequest()
                .authenticated())
                .csrf(config -> config.disable())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
