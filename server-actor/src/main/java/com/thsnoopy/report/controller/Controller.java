package com.thsnoopy.report.controller;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/postSomething")
@Slf4j
public class Controller {
    @PostMapping("/{id}")
    public Mono<Response> postSomething(@PathVariable long id) {
        return Mono.just(Response.builder().id(id).build());
    }
}

@Data
@Builder
class Response {
    private long id;
}