package com.thsnoopy.report.controller;

import com.thsnoopy.report.api.SampleApiClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/requestPost")
@AllArgsConstructor
public class Controller {
    private SampleApiClient sampleApiClient;
    @GetMapping
    public Mono<String> trigger() {
//        return sampleApiClient.postSomethingWithExchange()
//                .map(results -> "success");

        int count = 10000;
        List list = new ArrayList<Integer>();

        for (int i = 0; i < count; i ++) {
            list.add(i);
        }

        // simulate concurrent API calls
        return Flux.fromStream(list.stream())
                .flatMap(id -> sampleApiClient.postSomethingWithExchange())
                .collectList()
                .map(results -> "success");
    }
}
