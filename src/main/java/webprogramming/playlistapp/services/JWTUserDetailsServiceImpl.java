package webprogramming.playlistapp.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import webprogramming.playlistapp.entities.Role;
import webprogramming.playlistapp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class JWTUserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private UserService userService;

    public JWTUserDetailsServiceImpl(@Qualifier("userRepository") UserRepository userRepository,
                                     @Qualifier("userService") UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        webprogramming.playlistapp.entities.User applicationUser = userRepository.findByEmail(email);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(applicationUser.getEmail(), applicationUser.getPass(),
                userService.getUserAuthoritiesByEmail(applicationUser.getEmail()));
    }

}
