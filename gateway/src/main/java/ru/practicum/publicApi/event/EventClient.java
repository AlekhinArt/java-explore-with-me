package ru.practicum.publicApi.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.publicApi.event.dto.Sort;

import java.util.List;
import java.util.Map;

@Service
public class EventClient extends BaseClient {

    @Autowired
    public EventClient(@Value("${main-service-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }
    public ResponseEntity<Object> getEvents(String text, List<Long> categories,
                                            Boolean paid, String rangeStart, String rangeEnd,
                                            Boolean onlyAvailable, Sort sort, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "text", text,
                "categories", categories,
                "paid", paid,
                "rangeStart", rangeStart,
                "rangeEnd", rangeEnd,
                "onlyAvailable", onlyAvailable,
                "sort", sort,
                "from", from,
                "size", size
        );
        return get("/events?text={text}&categories={categories}&paid={paid}&rangeStart={rangeStart}&rangeEnd={rangeEnd}" +
                "&onlyAvailable={onlyAvailable}&sort={sort}&from={from}&size={size}", parameters);

    }

    public ResponseEntity<Object> getEvent(Long eventId) {
        return get("/events/" + eventId);

    }
}
