package ru.practicum.privateApi.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;

import java.util.Map;

@Service
public class RequestClient extends BaseClient {

    @Autowired
    public RequestClient(@Value("${main-service-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getRequestsThisUser(Long userId, Long eventId) {
        return get("/users/" + userId + "/events/" + eventId + "/requests");
    }

    public ResponseEntity<Object> confirmOtherRequestsThisUser(Long userId, Long eventId, Long reqId) {
        return patch("/users/" + userId + "/events/" + eventId + "/requests/" + reqId + "/confirm");
    }

    public ResponseEntity<Object> rejectOtherRequestsThisUser(Long userId, Long eventId, Long reqId) {
        return patch("/users/" + userId + "/events/" + eventId + "/requests/" + reqId + "/reject");
    }

    public ResponseEntity<Object> getRequestsThisUserOtherEvents(Long userId) {
        return get("/users/" + userId + "/requests");
    }

    public ResponseEntity<Object> addRequestsThisUserOtherEvents(Long userId, Long eventId) {
        Map<String, Object> parameters = Map.of(
                "eventId", eventId);
        return post("/users/" + userId + "/requests?eventId={eventId}", parameters);
    }

    public ResponseEntity<Object> cancelRequestsThisUserOtherEvents(Long userId, Long requestId) {
        return patch("/users/" + userId + "/requests/" + requestId + "/cancel");
    }


}
