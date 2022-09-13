package com.holtzhausenh.elsevier.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class TaskDto {

    @NotEmpty(message = "Title is mandatory")
    private String title;

    @NotEmpty(message = "Description is mandatory")
    private String description;

    private boolean finished;
}
