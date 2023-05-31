package org.tfg.spring.Vape.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tfg.spring.Vape.entities.Rol;
import org.tfg.spring.Vape.entities.User;
import org.tfg.spring.Vape.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
    
    /*
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByMail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No se encontro al usuario con email : " + email);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        return new org.springframework.security.core.userdetails.User(
          user.getMail(), user.getPassword(), enabled, accountNonExpired,
          credentialsNonExpired, accountNonLocked, getAuthorities(user.getRoles()));
    }
    */

    public UserDetails loadUserByUsername(String email) 
      throws UsernameNotFoundException {
     
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        try {
            User user = userRepository.findByMail(email);
            if (user == null) {
                throw new UsernameNotFoundException(
                  "No user found with username: " + email);
            }
            
            return new org.springframework.security.core.userdetails.User(
              user.getMail(), 
              user.getPassword().toLowerCase(), 
              user.isEnabled(), 
              accountNonExpired, 
              credentialsNonExpired, 
              accountNonLocked, 
              getAuthorities(user.getRol()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static List<GrantedAuthority> getAuthorities (Rol rol) {
    
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        authorities.add(new SimpleGrantedAuthority(rol.getNombre()));
 
        return authorities;
        
    }
}
