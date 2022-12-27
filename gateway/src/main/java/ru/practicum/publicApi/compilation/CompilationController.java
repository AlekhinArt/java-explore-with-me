package ru.practicum.publicApi.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class CompilationController {
    private final CompilationClient compilationClient;

    @GetMapping("/compilations")
    public ResponseEntity<Object> getCompilations(@RequestParam(required = false, defaultValue = "false") Boolean pinned,
                                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get Compilations with params pinned: {}, from: {}, size: {};", pinned, from, size);
        return compilationClient.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<Object> getEvent(@Positive @PathVariable Long compId) {
        log.info("Get event with params pinned: {};", compId);
        return compilationClient.getCompilation(compId);
    }
}
