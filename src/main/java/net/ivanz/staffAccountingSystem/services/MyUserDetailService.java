package net.ivanz.staffAccountingSystem.services;

import net.ivanz.staffAccountingSystem.exceptions.RestException;
import net.ivanz.staffAccountingSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

    }
}
