package ru.practicum.exception;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayError {
    private String message;
    private String reason;
    private String status;
    private String timestamp;
}
