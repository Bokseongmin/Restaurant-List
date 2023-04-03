package com.example.demo.repository;

import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void queryMethodTest() {
        var usersOptional = userRepository.findByCreatedAtAfter(LocalDateTime.now()
                .minusMonths(1L));

        usersOptional.ifPresent(users->{
            users.forEach(System.out::println);
        });
    }

    @Rollback(value = false)
    @Transactional
    @Test
    void relationalQueryTest() {
        var user = userRepository.findByNick("reloadUp");
        user.ifPresent(u->{
            var review = Review.builder().title("good title")
                    .contents("good contents")
                    .user(u)
                    .build();

            review = reviewRepository.save(review);
            System.out.println(review);
        });
    }

    @Transactional
    @Test
    void relationalQuerySelectTest() {
        var review  = reviewRepository.findById(4L);
        review.ifPresent(r->{
            System.out.println(r.getUser().getNick());
        });

        var user = userRepository.findByNick("reloadUp");
        user.ifPresent(u->{
            u.getReviews().forEach(System.out::println);
        });
    }

    @Test
    void addDummyUserTest() {
        var user1= User.builder().userId("cocomo1")
                .userPw("1234")
                .addr("Tokyo1")
                .nick("angel")
                .build();
        var user2 = User.builder().userId("cocomo2")
                .userPw("1234")
                .addr("Tokyo2")
                .nick("angel")
                .build();
        var user3 = User.builder().userId("cocomo3")
                .userPw("1234")
                .addr("Tokyo3")
                .nick("angel")
                .build();
        var users = Arrays.asList(user1, user2, user3);
        userRepository.saveAll(users);

        var findUsers = userRepository.findByNickLike("angel");
        findUsers.ifPresent(s->{
            System.out.println(s);
        });
    }

    @Test
    void pageableTest() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Order.desc("idx")));
        var pageOpt = userRepository.findByNickLike("angel%", pageable);
        pageOpt.ifPresent(pages->{
            System.out.println(pages.getTotalElements());
            pages.getContent().forEach(System.out::println);
            System.out.println(pages.getTotalElements());
        });
    }
}