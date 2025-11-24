package com.algaworks.algacomments.comment.service.api.controller;

import com.algaworks.algacomments.comment.service.api.exception.CommentRejectedException;
import com.algaworks.algacomments.comment.service.api.exchange.moderation.client.ModerationClient;
import com.algaworks.algacomments.comment.service.api.exchange.moderation.model.ModerationRequest;
import com.algaworks.algacomments.comment.service.api.exchange.moderation.model.ModerationResponse;
import com.algaworks.algacomments.comment.service.api.model.CommentInput;
import com.algaworks.algacomments.comment.service.api.model.CommentOutput;
import com.algaworks.algacomments.comment.service.common.UUIDUtils;
import com.algaworks.algacomments.comment.service.domain.model.Comment;
import com.algaworks.algacomments.comment.service.domain.repository.CommentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RequestMapping("/api/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final ModerationClient moderationClient;
    private final CommentRepository commentRepository;

    @PostMapping
    @Transactional
    @ResponseStatus(CREATED)
    public CommentOutput createComment(@RequestBody @Valid final CommentInput input) {
        final Comment comment = toNewEntity(input);

        final ModerationRequest moderationRequest = ModerationRequest.builder()
                .commentId(comment.getId())
                .text(comment.getText())
                .build();
        final ModerationResponse moderationResponse = moderationClient.moderate(moderationRequest);

        if (moderationResponse.getApproved() == null || !moderationResponse.getApproved()) {
            throw new CommentRejectedException(moderationResponse.getReason());
        }

        final Comment saved = commentRepository.save(comment);
        return toOutput(saved);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public CommentOutput findComment(@PathVariable("id") final UUID id) {
        final Comment entity = commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        return toOutput(entity);
    }

    @GetMapping
    @ResponseStatus(OK)
    public Page<CommentOutput> findAllComments(@PageableDefault final Pageable pageable) {
        return commentRepository.findAll(pageable).map(this::toOutput);
    }

    private Comment toNewEntity(final CommentInput input) {
        if (input == null) {
            return null;
        }
        return Comment.builder()
                .id(UUIDUtils.generateTimeBasedUUID())
                .author(input.getAuthor())
                .text(input.getText())
                .createdAt(OffsetDateTime.now())
                .build();
    }

    private CommentOutput toOutput(final Comment entity) {
        if (entity == null) {
            return null;
        }
        return CommentOutput.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .text(entity.getText())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
