package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNick(String nick);

    Optional<User> findByUserId(String userId);
    Optional<List<User>> findByCreatedAtAfter(LocalDateTime createdAt);
    Optional<List<User>> findByNickLike(String nickStr);
    Optional<Page<User>> findByNickLike(String nickStr, Pageable pageable);
    Optional<User> findTopByNickOrderByIdxDesc(String nick);
}
