package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoodResDto {
    private String title;
    private String category;
    private String address;
    @JsonProperty(value = "road_address")
    private String roadAddress;
    @JsonProperty(value = "homepage_link")
    private String homepageLink;
    @JsonProperty(value = "image_link")
    private String imageLink;
}
