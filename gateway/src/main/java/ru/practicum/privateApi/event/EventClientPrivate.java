package ru.practicum.privateApi.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.privateApi.event.dto.EventDto;
import ru.practicum.privateApi.event.dto.EventUpdateDto;

import java.util.Map;

@Service
public class EventClientPrivate extends BaseClient {

    @Autowired
    public EventClientPrivate(@Value("${main-service-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getEventsCreatedThisUser(Long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("/users/" + userId + "/events", parameters);
    }

    public ResponseEntity<Object> getEventCreatedThisUser(Long userId, Long eventId) {
        return get("/users/" + userId + "/events/" + eventId);
    }

    public ResponseEntity<Object> changeEventCreatedThisUser(Long userId, EventUpdateDto eventDto) {
        return patch("/users/" + userId + "/events", eventDto);
    }

    public ResponseEntity<Object> createEventThisUser(Long userId, EventDto eventDto) {
        return post("/users/" + userId + "/events", eventDto);
    }

    public ResponseEntity<Object> cancelEventCreatedThisUser(Long userId, Long eventId) {
        return patch("/users/" + userId + "/events/" + eventId);
    }
}