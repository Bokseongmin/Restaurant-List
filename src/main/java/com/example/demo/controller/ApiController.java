package com.example.demo.controller;

import com.example.demo.naver.dto.ImageSearchReqDto;
import com.example.demo.naver.dto.LocalSearchReqDto;
import com.example.demo.service.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/apis")
public class ApiController {
    private final NaverService naverService;

    /*
     * http://localhost:8080/apis/search/local?query=
     * */
    @GetMapping(value = "/search/local")
    public ResponseEntity findLocalSearch(@RequestParam("query") String query) {
        var reqDto = LocalSearchReqDto.builder().query(query).build();
        return ResponseEntity.status(HttpStatus.OK).body(naverService.findLocalSearch(reqDto));
    }

    /*
     * http://localhost:8080/apis/search/image?query=
     * */
    @GetMapping(value = "/search/image")
    public ResponseEntity findImageSearch(@RequestParam("query") String query) {
        var reqDto = ImageSearchReqDto.builder().query(query).build();
        return ResponseEntity.status(HttpStatus.OK).body(naverService.findImageSearch(reqDto));
    }

    /*
     * http://localhost:8080/apis/search?query=
     * */
    @GetMapping(value = "/search")
    public ResponseEntity findSearch(@RequestParam("query") String query) {
        return ResponseEntity.status(HttpStatus.OK).body(naverService.search(query));
    }

    @GetMapping(value = "/searches")
    public ResponseEntity findSearches(@RequestParam("query") String query) {
        return ResponseEntity.status(HttpStatus.OK).body(naverService.searches(query));
    }
}
