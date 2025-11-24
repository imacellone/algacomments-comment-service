package com.algaworks.algacomments.comment.service.api.exchange.moderation.model;

import lombok.Data;

@Data
public class ModerationResponse {
    private final Boolean approved;
    private final String reason;
}
