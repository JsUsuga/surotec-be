package com.website.surotec_academy.domain.dto.news;

import com.website.surotec_academy.enums.NewsStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NewsDto(
        Integer idNews,

        @NotNull
        Integer employeeId,

        @NotNull
        @Size(max = 200)
        String title,

        @NotNull
        String content,

        LocalDateTime publishDate,

        @NotNull
        String urlImage,

        @NotNull
        NewsStatus status
) {}
