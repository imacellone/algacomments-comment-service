package com.algaworks.algacomments.comment.service.api.exchange.moderation.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ModerationRequest {
    private UUID commentId;
    private String text;
}
