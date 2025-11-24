package com.algaworks.algacomments.comment.service.api.exception;

public class CommentRejectedException extends RuntimeException {

    public CommentRejectedException(final String message) {
        super(message);
    }
}
