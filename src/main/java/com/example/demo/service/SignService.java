package com.example.demo.service;

import com.example.demo.dto.FoodResDto;
import com.example.demo.dto.MyFoodDto;
import com.example.demo.entity.MyFood;
import com.example.demo.entity.User;
import com.example.demo.repository.MyFoodRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class SignService {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserRepository userRepository;
    @Resource
    MyFoodRepository myFoodRepository;

    public User getAccount(User user) {
        Optional<User> account = userRepository.findByUserId(user.getUserId());
        User getUser = null;
        if (account.isPresent()) {
            getUser = account.get();
        } else {
            return null;
        }
        if (getUser.getUserPw().equals(user.getUserPw())) {
            return getUser;
        }
        return null;
    }

    public void createAccount(User user) {
        User account = User.builder().userId(user.getUserId())
                .userPw(user.getUserId())
                .nick(user.getNick())
                .addr(user.getAddr())
                .build();

        userRepository.save(account);
    }

    public List<MyFoodDto> getMyFood(Long userIdx) {
        List<MyFood> myFoods = myFoodRepository.findByUserIdx(userIdx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다 : " + userIdx));

        List<MyFoodDto> myFoodDtos = new ArrayList<>();
        for (MyFood myFood : myFoods) {
            MyFoodDto myFoodDto = new MyFoodDto(myFood.getIdx(), myFood.getTitle(), myFood.getCategory(), myFood.getRoadAddress(), myFood.getCreatedAt(), myFood.getUpdatedAt());
            myFoodDtos.add(myFoodDto);
        }
        return myFoodDtos;
    }

    // EntityManager로 영속화하기 전에 트랜잭션을 시작해야 됨.
    @Transactional
    public void add(User user, MyFood myFood) {
        // 객체와 DB를 분리하고 다시 연결할 때 오류가 발생할 수 있으므로 merge이용
        User _user = entityManager.merge(user);
        myFood.setUser(_user);
        myFoodRepository.save(myFood);
    }

    public void delete(String query) {
        Long foodIdx = Long.valueOf(query);
        myFoodRepository.deleteById(foodIdx);
    }
}
