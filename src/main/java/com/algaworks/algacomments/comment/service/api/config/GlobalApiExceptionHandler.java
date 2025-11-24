package com.algaworks.algacomments.comment.service.api.config;

import com.algaworks.algacomments.comment.service.api.exception.CommentRejectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.channels.ClosedChannelException;
import java.util.Map;

@RestControllerAdvice
public class GlobalApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommentRejectedException.class)
    public ProblemDetail handle(final CommentRejectedException exception) {
        return createProblemDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Rejected Comment",
                exception.getMessage(),
                null);
    }

    @ExceptionHandler({
            SocketTimeoutException.class,
            ConnectException.class,
            ClosedChannelException.class
    })
    public ProblemDetail handle(final IOException exception) {
        return createProblemDetail(HttpStatus.GATEWAY_TIMEOUT, "Gateway Timeout", null, null);
    }

    private ProblemDetail createProblemDetail(final HttpStatus status,
                                              final String title,
                                              final String detail,
                                              final Map<String, Object> properties) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(title);
        problemDetail.setDetail(detail);
        problemDetail.setProperties(properties);
        problemDetail.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/" + status.value()));
        return problemDetail;
    }
}
