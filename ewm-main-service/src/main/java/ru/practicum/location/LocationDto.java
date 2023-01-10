package ru.practicum.location;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class LocationDto {
    private double lat;
    private double lon;
}
