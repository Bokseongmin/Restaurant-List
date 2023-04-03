package com.example.demo.entity;

import com.example.demo.entity.listener.DateListener;
import com.example.demo.entity.listener.LibraryEntityListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(value = LibraryEntityListener.class)
@Table(name = "myfood")
public class MyFood implements DateListener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String title;
    private String category;
    private String roadAddress;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "user_idx")
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
