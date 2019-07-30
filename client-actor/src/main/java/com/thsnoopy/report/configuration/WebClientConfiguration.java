package com.thsnoopy.report.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Configuration
public class WebClientConfiguration {
    @Qualifier("sampleWebClient")
    @Bean
    public WebClient sampleWebClient(WebClient.Builder builder,
                                   @Value("${api.sample.endpoint}") String endpoint,
                                   @Value("${api.sample.connectTimeoutInMilliSecond}") int connectTimeoutInMilliSecond,
                                   @Value("${api.sample.readWriteTimeoutInSecond}") int readWriteTimeoutInSecond) {

        return builder
                .uriBuilderFactory(uriBuilderFactory(endpoint))
                .clientConnector(clientHttpConnector(connectTimeoutInMilliSecond, readWriteTimeoutInSecond))
                .build();
    }

    private DefaultUriBuilderFactory uriBuilderFactory(String baseUri) {
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(baseUri);
        return defaultUriBuilderFactory;
    }

    private ClientHttpConnector clientHttpConnector(int connectTimeoutInMilliSecond, int readWriteTimeoutInSecond) {
        HttpClient httpClient =
            HttpClient.create()
//                    .keepAlive(false);
                    .tcpConfiguration(client ->
                            client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutInMilliSecond)
                                    .doOnConnected(conn -> conn
                                                .addHandlerLast(new ReadTimeoutHandler(readWriteTimeoutInSecond))
                                                .addHandlerLast(new WriteTimeoutHandler(readWriteTimeoutInSecond))
                                    )
                    );
        return new ReactorClientHttpConnector(httpClient);
    }
}
