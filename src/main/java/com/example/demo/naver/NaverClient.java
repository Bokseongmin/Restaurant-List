package com.example.demo.naver;

import com.example.demo.naver.dto.ImageSearchReqDto;
import com.example.demo.naver.dto.ImageSearchResDto;
import com.example.demo.naver.dto.LocalSearchReqDto;
import com.example.demo.naver.dto.LocalSearchResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class NaverClient {

    @Value("${naver.client.id}")
    private String naverClientId;
    @Value("${naver.client.secret}")
    private String naverSecret;
    @Value("${naver.url.search.local}")
    private String naverLocalSearchUrl;
    @Value("${naver.url.search.image}")
    private String naverImageSearchUrl;

    public LocalSearchResDto localSearch(LocalSearchReqDto localSearchReqDto) {
        var uri = UriComponentsBuilder.fromUriString(naverLocalSearchUrl)
                .queryParam("query", localSearchReqDto.getQuery())
                .queryParam("display", localSearchReqDto.getDisplay())
                .queryParam("start", localSearchReqDto.getStart())
                .queryParam("sort", localSearchReqDto.getSort())
                .build()
                .encode()
                .toUri();

        log.info("uri={}", uri.toString());

//        return uri.toString();
        var headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", naverClientId);
        headers.add("X-Naver-Client-Secret", naverSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = RequestEntity.get(uri)
                .headers(headers).build();

        var result = new RestTemplate().exchange(httpEntity, new ParameterizedTypeReference<LocalSearchResDto>() {});

        log.info("response header = {}", result.getHeaders());
        log.info("response status = {}", result.getStatusCode());
        log.info("response body = {}", result.getBody());

        return result.getBody();
    }
    public ImageSearchResDto imageSearch(ImageSearchReqDto imageSearchReqDto) {
        var uri = UriComponentsBuilder.fromUriString(naverImageSearchUrl)
                .queryParams(imageSearchReqDto.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        log.info("uri={}", uri.toString());

        var headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", naverClientId);
        headers.add("X-Naver-Client-Secret", naverSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = RequestEntity.get(uri)
                .headers(headers).build();

        var result = new RestTemplate().exchange(httpEntity,
                new ParameterizedTypeReference<ImageSearchResDto>(){});

//        log.info("response header = {}", result.getHeaders());
//        log.info("response status = {}", result.getStatusCode());
//        log.info("response body = {}", result.getBody());

        return result.getBody();
    }
}
