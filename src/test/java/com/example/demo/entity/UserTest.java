package com.example.demo.entity;

import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void userAddTest() {
        var user = User.builder().userId("coolmax")
                .userPw("1234")
                .addr("newyork street 13st")
                .nick("developer")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        user = userRepository.save(user);
        System.out.println(user);
    }

    @Test
    void findUserTest() {
        var optionalUser = userRepository.findById(3L);
        optionalUser.ifPresent(System.out::println);
    }

    @Test
    void signUpTest() {
        var user = userRepository.findByUserId("cocomo3");
        var test = "1234";
        if(user == null) {
        }
        var pass = user.get().getUserPw();
        if(pass.equals(test)) {
            System.out.println("성공");
        } else {
            System.out.println("실패");
        }
    }
}