package com.example.demo.repository;

import com.example.demo.entity.MyFood;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyFoodRepositoryTest {

    @Autowired
    MyFoodRepository myFoodRepository;

    @Autowired
    UserRepository userRepository;

    @Rollback(value = false)
    @Transactional
    @Test
    void addFoodTest(){
        var findUser = userRepository.findById(6L).orElseThrow();
        var myfood = MyFood.builder().title("맛있는 갈비").category("한식>갈비").roadAddress("경산시")
                .user(findUser)
                .build();

        System.out.println(myFoodRepository.save(myfood));
    }

    @Transactional
    @Test
    void findMyFoodTest() {
        var findUser = userRepository.findById(6L).orElseThrow();
        findUser.getFoods().forEach(System.out::println);
    }
}