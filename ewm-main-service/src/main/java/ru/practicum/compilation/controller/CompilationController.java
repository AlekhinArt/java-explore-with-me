package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.service.CompilationService;

import java.util.Collection;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping("/compilations")
    public Collection<CompilationDto> getCompilations(@RequestParam(required = false, defaultValue = "false") Boolean pinned,
                                                      @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get Compilations with params pinned: {}, from: {}, size: {};", pinned, from, size);
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getEvent(@PathVariable Long compId) {
        log.info("Get event with params pinned: {};", compId);
        return compilationService.getCompilation(compId);
    }

    @PostMapping("/admin/compilations")
    public CompilationDto addCompilation(@RequestBody NewCompilationDto compilation) {
        return compilationService.addCompilation(compilation);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/admin/compilations/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId,
                                           @PathVariable Long eventId) {
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/admin/compilations/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Long compId,
                                      @PathVariable Long eventId) {
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/admin/compilations/{compId}/pin")
    public void deleteCompilationFromPin(@PathVariable Long compId) {
        compilationService.changeCompilationPin(compId, false);
    }

    @PatchMapping("/admin/compilations/{compId}/pin")
    public void addCompilationToPin(@PathVariable Long compId) {
        compilationService.changeCompilationPin(compId, true);
    }
}
