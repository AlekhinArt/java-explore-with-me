package ru.practicum.adminApi.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminApi.compilation.dto.NewCompilationDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
@Validated
public class CompilationControllerAdmin {

    private final CompilationClientAdmin compilationClient;

    @PostMapping
    public ResponseEntity<Object> addCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return compilationClient.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> deleteCompilation(@Positive @PathVariable Long compId) {
        return compilationClient.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Object> deleteEventFromCompilation(@Positive @PathVariable Long compId,
                                                             @Positive @PathVariable Long eventId) {
        return compilationClient.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Object> addEventToCompilation(@Positive @PathVariable Long compId,
                                                        @Positive @PathVariable Long eventId) {
        return compilationClient.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public ResponseEntity<Object> deleteCompilationFromPin(@Positive @PathVariable Long compId) {
        return compilationClient.deleteCompilationFromPin(compId);
    }

    @PatchMapping("/{compId}/pin")
    public ResponseEntity<Object> addCompilationToPin(@Positive @PathVariable Long compId) {
        return compilationClient.addCompilationToPin(compId);
    }


}
