package ru.practicum.privateApi.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.privateApi.comment.dto.CommentDto;


@Service
public class CommentClientPrivate extends BaseClient {

    @Autowired
    public CommentClientPrivate(@Value("${main-service-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }


    public ResponseEntity<Object> createComment(Long userId, Long eventId, CommentDto commentDto) {
        return post("/users/" + userId + "/events/" + eventId + "/comments", commentDto);
    }

    public ResponseEntity<Object> deleteComment(Long commentId, Long userId) {
        return delete("/users/" + userId + "/comments/" + commentId);
    }

    public ResponseEntity<Object> changeAbilityToComment(Long userId, Long eventId) {
        return patch("/users/" + userId + "/events/" + eventId + "/comments");
    }
}
