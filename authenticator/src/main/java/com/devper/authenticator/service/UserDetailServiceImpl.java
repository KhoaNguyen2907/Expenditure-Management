package com.devper.authenticator.service;

import com.devper.authenticator.dao.AuthenticatorDAO;
import com.devper.authenticator.model.AppUser;
import com.devper.common.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    AuthenticatorDAO authDAO;
    public UserDetailServiceImpl(AuthenticatorDAO authDAO) {
        this.authDAO = authDAO;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         AppUser appUser = authDAO.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found with username: " + username));
        return org.springframework.security.core.userdetails.User.withUsername(username).password(appUser.getPassword()).authorities("USER").build();
    }
}
