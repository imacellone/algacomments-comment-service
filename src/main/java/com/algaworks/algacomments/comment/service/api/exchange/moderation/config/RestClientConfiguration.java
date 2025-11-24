package com.algaworks.algacomments.comment.service.api.exchange.moderation.config;

import com.algaworks.algacomments.comment.service.api.exchange.moderation.client.ModerationClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfiguration {

    @Bean
    public ModerationClient moderationClient(final RestClientFactory factory) {
        final RestClient restClient = factory.buildModerationClient();
        final RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        return HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(restClientAdapter)
                .build()
                .createClient(ModerationClient.class);
    }
}
