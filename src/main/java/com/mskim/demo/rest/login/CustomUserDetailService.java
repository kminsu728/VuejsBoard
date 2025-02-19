package com.mskim.demo.rest.login;

import com.mskim.demo.rest.user.User;
import com.mskim.demo.rest.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(user);

//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUserId())
//                .password(user.getPassword())
//                .roles(user.getRole())
//                .build();
    }
}
