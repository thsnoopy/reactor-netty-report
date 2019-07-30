package com.thsnoopy.report.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class SampleApiClient {
    private final static String PATH = "/postSomething/{id}";
    private WebClient sampleWebClient;

    public Mono<Boolean> postSomethingWithExchange() {
        return sampleWebClient.post().uri(PATH, System.currentTimeMillis())
                .exchange()


//                .flatMap(clientResponse -> {
//                            if (clientResponse.statusCode() == HttpStatus.OK) {
//                                return Mono.just(true);
//                            } else {
//                                return Mono.just(false);
//                            }
//                        });

                .flatMap(clientResponse -> clientResponse.bodyToMono(Response.class)
                        .map(response -> response.getId() > 0)
                );
    }

    public Mono<Boolean> postSomethingWithRetrieve() {
        return sampleWebClient.post().uri(PATH, System.currentTimeMillis())
                .retrieve()
                .bodyToMono(Response.class)
                .map(response -> response.getId() > 0);

    }
}

@Data
class Response {
    private long id;
}