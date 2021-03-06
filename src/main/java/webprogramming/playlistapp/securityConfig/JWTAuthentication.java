package webprogramming.playlistapp.securityConfig;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import webprogramming.playlistapp.services.UserDetailsImpl;
import webprogramming.playlistapp.services.UserDetailsServiceImpl;
import webprogramming.playlistapp.services.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static webprogramming.playlistapp.securityConfig.JWTConstants.*;

public class JWTAuthentication extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private UserDetailsServiceImpl userDetailsService;
    private static Logger logger = LoggerFactory.getLogger(JWTAuthentication.class);

    public JWTAuthentication(AuthenticationManager authenticationManager,
                             @Qualifier("userDetailsService") UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            webprogramming.playlistapp.entities.User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), webprogramming.playlistapp.entities.User.class);


            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((UserDetailsImpl) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        String isUserAdmin = " ";
        boolean isAdmin = ((UserDetailsImpl) auth.getPrincipal())
                .getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if(isAdmin){
            isUserAdmin = "true";
        }
        else{
            isUserAdmin = "false";
        }
        res.addHeader("User-Role", isUserAdmin);
    }
}