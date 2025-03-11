package com.mskim.demo.base.component;

import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VuejsExceptionType;
import com.mskim.demo.rest.user.User;
import com.mskim.demo.rest.user.UserRepository;
import com.mskim.demo.rest.board.Board;
import com.mskim.demo.rest.board.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            if (userRepository.findByUserId("admin") == null) {
                User admin = User.builder()
                        .userId("admin")
                        .name("ADMIN")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@example.com")
                        .roles(Collections.singletonList("ADMIN"))
                        .enabled(true).build();
                userRepository.save(admin);
            }

            if (userRepository.findByUserId("test") == null) {
                User testUser =  User.builder()
                        .userId("test")
                        .name("TEST")
                        .password(passwordEncoder.encode("test"))
                        .email("test@example.com")
                        .roles(Collections.singletonList("USER"))
                        .enabled(true).build();
                userRepository.save(testUser);
            }

            if (boardRepository.findByType("qna") == null) {
                Board board = Board.builder()
                        .type("qna")
                        .name("Q&A").build();
                boardRepository.save(board);

            }

            if (boardRepository.findByType("community") == null) {
                Board board = Board.builder()
                        .type("community")
                        .name("자유게시판").build();
                boardRepository.save(board);
            }
        } catch (Exception e ) {
            throw new VuejsException(VuejsExceptionType.data_initialize_fail);
        }
    }
}
