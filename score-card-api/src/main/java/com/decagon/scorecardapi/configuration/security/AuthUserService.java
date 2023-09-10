package com.decagon.scorecardapi.configuration.security;

import com.decagon.scorecardapi.exception.AccountNotActiveException;
import com.decagon.scorecardapi.model.CustomUserDetail;
import com.decagon.scorecardapi.model.User;
import com.decagon.scorecardapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(
                email + " was not found"));
       if(user.getIsAccountActive()) {
           return new CustomUserDetail(user);
       }else
           throw new AccountNotActiveException("User account is not active");

    }
}


