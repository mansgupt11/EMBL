package com.ebi.technicaltest.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUserDetailService  implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(final String s) throws UsernameNotFoundException {
    return new User("admin", "admin", List.of());
    }
}
