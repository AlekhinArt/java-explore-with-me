package ru.practicum.adminApi.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.adminApi.categories.dto.Categories;
import ru.practicum.client.BaseClient;

@Service
public class CategoriesClient extends BaseClient {
    @Autowired
    public CategoriesClient(@Value("${main-service-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/admin/categories"))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> changeCategory(Categories categories) {
        return patch("", categories);
    }

    public ResponseEntity<Object> createCategory(Categories categories) {
        return post("", categories);
    }

    public ResponseEntity<Object> deleteCategory(Integer catId) {
        return delete("/" + catId);
    }
}
