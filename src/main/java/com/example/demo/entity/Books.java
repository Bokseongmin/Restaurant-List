package com.example.demo.entity;

import com.example.demo.entity.listener.DateListener;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Books implements DateListener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String title;
    private String publisher;

    @ToString.Exclude
    @OneToMany(mappedBy = "books")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 외부에서 리뷰를 작성하여 추가할 수 있음
    public void addReview(Review review) {
        this.reviews.add(review);
    }
}
