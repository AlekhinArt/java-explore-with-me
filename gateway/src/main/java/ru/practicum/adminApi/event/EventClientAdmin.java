package ru.practicum.adminApi.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.adminApi.dto.EventUpdateDto;
import ru.practicum.adminApi.dto.State;
import ru.practicum.client.BaseClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EventClientAdmin extends BaseClient {
    @Autowired
    public EventClientAdmin(@Value("${main-service-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/admin/events"))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> searchEvents(List<String> users, List<State> states,
                                               List<String> categories, String rangeStart, String rangeEnd,
                                               Integer from, Integer size) {
        String user = String.join(", ", users);
        List<String> stateList = new ArrayList<>();
        for (State state : states) {
            stateList.add(state.toString());
        }
        String state = String.join(", ", stateList);
        String category = String.join(", ", categories);


        Map<String, Object> parameters = Map.of(
                "users", user,
                "states", state,
                "categories", category,
                "rangeStart", rangeStart,
                "rangeEnd", rangeEnd,
                "from", from,
                "size", size
        );
        return get("?users={users}&states={states}&categories={categories}&rangeStart={rangeStart}" +
                "&rangeEnd={rangeEnd}&from={from}&size={size}", parameters);


    }

    public ResponseEntity<Object> updateEvent(Long eventId, EventUpdateDto eventDto) {
        return put("/" + eventId, eventDto);

    }

    public ResponseEntity<Object> publishEvent(Long eventId) {
        return patch("/" + eventId + "/publish");
    }

    public ResponseEntity<Object> rejectEvent(Long eventId) {
        return patch("/" + eventId + "/reject");
    }
}
