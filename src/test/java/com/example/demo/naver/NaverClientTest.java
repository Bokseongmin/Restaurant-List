package com.example.demo.naver;

import com.example.demo.naver.dto.LocalSearchReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NaverClientTest {

    @Autowired
    NaverClient naverClient;

    @Test
    void uriMakeStrTest() {
        var searchDto = LocalSearchReqDto.builder()
                .query("국밥").build();
        naverClient.localSearch(searchDto);
    }
}