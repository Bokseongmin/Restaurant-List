package com.example.demo.service;

import com.example.demo.dto.FoodResDto;
import com.example.demo.naver.NaverClient;
import com.example.demo.naver.dto.ImageSearchReqDto;
import com.example.demo.naver.dto.ImageSearchResDto;
import com.example.demo.naver.dto.LocalSearchReqDto;
import com.example.demo.naver.dto.LocalSearchResDto;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class NaverService {
    private final NaverClient naverClient;

    public LocalSearchResDto findLocalSearch(LocalSearchReqDto reqDto) {
        return naverClient.localSearch(reqDto);
    }

    public ImageSearchResDto findImageSearch(ImageSearchReqDto reqDto) {
        return naverClient.imageSearch(reqDto);
    }

    public FoodResDto search(String query) {

        var foodResDto = FoodResDto.builder().build();

        /*
         * naver local search area
         * */
        var localSearchDto = LocalSearchReqDto.builder().query(query).build();
        var localResult = naverClient.localSearch(localSearchDto);
        var optItem = localResult.getItems().stream().findFirst();

        if (optItem.isPresent()) {
            var item = optItem.get();

            foodResDto.setTitle(item.getTitle());
            foodResDto.setCategory(item.getCategory());
            foodResDto.setAddress(item.getAddress());
            foodResDto.setRoadAddress(item.getRoadAddress());
            foodResDto.setHomepageLink(item.getLink());


            /*
             * naver image search area
             * */

            var imageSerachDto = ImageSearchReqDto.builder().query(item.getTitle()).build();
            var imageResult = naverClient.imageSearch(imageSerachDto);
            var optImageItem = imageResult.getItems().stream().findFirst();
            if (optImageItem.isPresent()) {
                foodResDto.setImageLink(optImageItem.get().getThumbnail());

            }
        }

        log.info("{} search result = ", foodResDto);

        return foodResDto;
    }

    public List<FoodResDto> searches(String query) {

        List<FoodResDto> foodResDtoList = new ArrayList<>();

        /*
         * naver local search area
         * */
        LocalSearchReqDto localSearchDto = LocalSearchReqDto.builder().query(query).build();
        var localResult = naverClient.localSearch(localSearchDto);
        var optItem = localResult.getItems();
        if (!optItem.isEmpty()) {
            for (int i = 0; i < optItem.size(); i++) {
                var item = optItem.get(i);
                FoodResDto foodResDto = FoodResDto.builder().build();

                foodResDto.setTitle(item.getTitle());
                foodResDto.setCategory(item.getCategory());
                foodResDto.setAddress(item.getAddress());
                foodResDto.setRoadAddress(item.getRoadAddress());
                foodResDto.setHomepageLink(item.getLink());


                /*
                 * naver image search area
                 * */

                ImageSearchReqDto imageSearchDto = ImageSearchReqDto.builder().query(item.getTitle()).build();
                var imageResult = naverClient.imageSearch(imageSearchDto);
                var optImageItem = imageResult.getItems();
                if (!optImageItem.isEmpty()) {
                    for (int j = 0; j < optImageItem.size(); j++) {
                        foodResDto.setImageLink(optImageItem.get(j).getThumbnail());
                    }
                }
                foodResDtoList.add(foodResDto);
            }
        }
        log.info("search result = {}", foodResDtoList);

        return foodResDtoList;
    }
}
