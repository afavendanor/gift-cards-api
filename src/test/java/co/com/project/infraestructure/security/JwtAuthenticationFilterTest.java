package co.com.project.infraestructure.security;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import co.com.project.application.dtos.UserDTO;
import co.com.project.infraestructure.security.filter.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.PrintWriter;
import java.util.List;

public class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private AuthenticationManager authenticationManager;
    private MockHttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws Exception {
        authenticationManager = mock(AuthenticationManager.class);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
        response = mock(HttpServletResponse.class);

        filterChain = mock(FilterChain.class);
        writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        request = new MockHttpServletRequest();
        request.setContentType("application/json");
    }

    @Test
    void shouldAuthenticateUserSuccessfully() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("password");

        request.setContent(new ObjectMapper().writeValueAsBytes(userDTO));


        User userDetails = new User("testUser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        Authentication result = jwtAuthenticationFilter.attemptAuthentication(request, response);

        assertNotNull(result);
        assertEquals("testUser", result.getName());
    }

    @Test
    void shouldReturnTokenOnSuccessfulAuthentication() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("password");

        request.setContent(new ObjectMapper().writeValueAsBytes(userDTO));

        User userDetails = new User("testUser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        jwtAuthenticationFilter.successfulAuthentication(request, response, filterChain, authentication);

        verify(response).addHeader(Mockito.eq("Authorization"), Mockito.contains("Bearer "));
        verify(writer).write(Mockito.contains("testUser"));
        verify(writer).write(Mockito.contains("You have successfully logged in!"));
        verify(response).setStatus(HttpStatus.OK.value());
    }

    @Test
    void shouldHandleFailedAuthentication() throws Exception {
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Invalid credentials"));

        jwtAuthenticationFilter.unsuccessfulAuthentication(request, response, new AuthenticationException("Error parsing request body"){});

        verify(writer).write(Mockito.contains("Incorrect username or password!"));
        verify(response).setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
