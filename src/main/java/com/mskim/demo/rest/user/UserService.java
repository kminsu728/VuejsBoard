package com.mskim.demo.rest.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User authenticate(String userId, String password) {
        User user = userRepository.findByUserId(userId);

        if(user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    public User getUser(String userId) {
        User user = userRepository.findByUserId(userId);
        return user;
    }

    public User updateUser(String userId, String name, String email) {
        User user = userRepository.findByUserId(userId);

        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);

        return user;
    }
}
