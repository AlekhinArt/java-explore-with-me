package ru.practicum.stats.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HitDto {
    private Long id;

    private String app;

    private String uri;

    private String ip;

    private String time;
}
