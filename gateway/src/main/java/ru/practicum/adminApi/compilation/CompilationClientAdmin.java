package ru.practicum.adminApi.compilation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.adminApi.compilation.dto.NewCompilationDto;
import ru.practicum.client.BaseClient;

@Service
public class CompilationClientAdmin extends BaseClient {

    @Autowired
    public CompilationClientAdmin(@Value("${main-service-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/admin/compilations"))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addCompilation(NewCompilationDto newCompilationDto) {
        return post("/", newCompilationDto);
    }

    public ResponseEntity<Object> deleteCompilation(Long compId) {
        return delete("/" + compId);
    }

    public ResponseEntity<Object> deleteEventFromCompilation(Long compId, Long eventId) {
        return delete("/" + compId + "/events/" + eventId);
    }

    public ResponseEntity<Object> addEventToCompilation(Long compId, Long eventId) {
        return patch("/" + compId + "/events/" + eventId);
    }

    public ResponseEntity<Object> deleteCompilationFromPin(Long compId) {
        return delete("/" + compId + "/pin");
    }

    public ResponseEntity<Object> addCompilationToPin(Long compId) {
        return patch("/" + compId + "/pin");
    }
}
