package ru.practicum.adminApi.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.adminApi.dto.State;
import ru.practicum.privateApi.event.dto.EventDto;

import java.util.List;
import java.util.Map;

@Service
public class EventClient extends BaseClient {
    @Autowired
    public EventClient(@Value("${main-service-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl+"/admin/events"))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> searchEvents(List<Long> users, List<State> states,
                                               List<Long> categories, String rangeStart, String rangeEnd,
                                               Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "users", users,
                "states", states,
                "categories", categories,
                "rangeStart", rangeStart,
                "rangeEnd", rangeEnd,
                "from", from,
                "size", size
        );
        return get("?users={users}&states={states}&categories={categories}&rangeStart={rangeStart}" +
                "&rangeEnd={rangeEnd}&from={from}&size={size}", parameters);


    }

    public ResponseEntity<Object> updateEvent(Long eventId, EventDto eventDto) {
        return put("/" + eventId, eventId, eventDto);

    }

    public ResponseEntity<Object> publishEvent(Long eventId) {
        return patch("/" + eventId + "/publish", eventId);
    }
}
