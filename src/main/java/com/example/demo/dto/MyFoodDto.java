package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// session을 위해 생성
public class MyFoodDto {
    private Long id;
    private String title;
    private String category;
    private String roadAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
