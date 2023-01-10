package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.request.model.ParticipationRequests;
import ru.practicum.request.model.StateRequest;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<ParticipationRequests, Long> {

    Collection<ParticipationRequests> getParticipationRequestsByEvent(long eventId);

    Collection<ParticipationRequests> findAllByRequester(long userId);

    Integer countByEventAndStatusEquals(Long eventId, StateRequest stateRequest);


}
