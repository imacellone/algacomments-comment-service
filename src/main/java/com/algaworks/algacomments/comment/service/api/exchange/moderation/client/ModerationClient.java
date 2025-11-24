package com.algaworks.algacomments.comment.service.api.exchange.moderation.client;

import com.algaworks.algacomments.comment.service.api.exchange.moderation.model.ModerationRequest;
import com.algaworks.algacomments.comment.service.api.exchange.moderation.model.ModerationResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/moderate")
public interface ModerationClient {

    @PostExchange
    ModerationResponse moderate(@RequestBody ModerationRequest request);
}
