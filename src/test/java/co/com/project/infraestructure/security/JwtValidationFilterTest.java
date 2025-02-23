package co.com.project.infraestructure.security;

import co.com.project.infraestructure.security.config.TokenJwtConfig;
import co.com.project.infraestructure.security.filter.JwtValidationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtValidationFilterTest {

    private JwtValidationFilter jwtValidationFilter;
    private AuthenticationManager authenticationManager;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private JwtParser jwtParser;
    private Claims claims;
    private Jws<Claims> jws;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtValidationFilter = new JwtValidationFilter(authenticationManager);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        jwtParser = mock(JwtParser.class);
        claims = mock(Claims.class);
        jws = mock(String.valueOf(Jwts.class));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test_user");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void shouldContinueFilterChainIfNoToken() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        jwtValidationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldAuthenticateIfTokenIsValid() throws ServletException, IOException {
        String token = generateValidToken("testUser");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        when(jwtParser.parseClaimsJws(anyString())).thenReturn(jws);
        when(jws.getPayload()).thenReturn(claims);

        jwtValidationFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("test_user", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldReturnUnauthorizedIfTokenIsInvalid() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer invalid.token");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        when(jwtParser.parseClaimsJws(anyString())).thenReturn(jws);
        when(jws.getPayload()).thenReturn(claims);

        jwtValidationFilter.doFilterInternal(request, response, filterChain);

        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(statusCaptor.capture());

        assertEquals(HttpStatus.UNAUTHORIZED.value(), statusCaptor.getValue());
    }


    private String generateValidToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", new ObjectMapper().valueToTree(Collections.emptyList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(TokenJwtConfig.SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
