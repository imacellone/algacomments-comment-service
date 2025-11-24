package com.algaworks.algacomments.comment.service.api.exchange.moderation.config;

import com.algaworks.algacomments.comment.service.api.exchange.moderation.exception.ModerationClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

    private final RestClient.Builder restClientBuilder;

    public RestClient buildModerationClient() {
        return restClientBuilder
                .baseUrl("http://localhost:8081")
                .requestFactory(buildRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, this::handleError)
                .build();
    }

    private ClientHttpRequestFactory buildRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.of(5, ChronoUnit.SECONDS));
        factory.setConnectTimeout(Duration.of(5, ChronoUnit.SECONDS));
        return factory;
    }

    private void handleError(final HttpRequest httpRequest, final ClientHttpResponse clientHttpResponse) {
        throw new ModerationClientException();
    }
}
