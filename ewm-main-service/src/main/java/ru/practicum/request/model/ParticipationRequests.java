package ru.practicum.request.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "participation_requests")
public class ParticipationRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;
    private LocalDateTime created;
    @Column(name = "event_id")
    private Long event;
    private long requester;
    @Enumerated(EnumType.STRING)
    private StateRequest status;

}
