package com.algaworks.algacomments.comment.service.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentInput {

    @Size(min = 2)
    @NotBlank
    private String text;

    @Size(min = 2)
    @NotBlank
    private String author;
}
