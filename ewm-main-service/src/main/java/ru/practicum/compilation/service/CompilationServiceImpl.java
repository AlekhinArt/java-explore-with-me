package ru.practicum.compilation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;

import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exceptions.NotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Collection<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return compilationRepository.findAllByPinned(pageable, pinned).stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        return CompilationMapper.toCompilationDto(compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with compId: " + compId + " not found")));
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilation) {
        Compilation compilation = CompilationMapper.toCompilation(newCompilation);
        compilation.setEvents(newCompilation.getEvents().stream()
                .map(event -> eventRepository.findById(event)
                        .orElseThrow(() -> new NotFoundException("Compilation with compId: " + event + " not found")))
                .collect(Collectors.toList()));
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        try {
            log.info("deleteCompilation id: {}", compId);
            compilationRepository.deleteById(compId);
        } catch (Exception e) {
            log.debug("deleteCompilation.NotFoundException(Compilation not found)");
            throw new NotFoundException("Compilation not found");
        }
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        try {
            log.info("deleteEventFromCompilation compId: {}, eventId: {}", compId, eventId);
            compilationRepository.deleteEventFromCompilation(compId, eventId);
        } catch (Exception e) {
            log.debug("deleteCompilation.NotFoundException(Compilation or event not found)");
            throw new NotFoundException("Compilation or event not found");
        }

    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {

        compilationRepository.addEventToCompilation(compId, eventId);
    }

    @Override
    public void changeCompilationPin(Long compId, boolean pin) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with compId: " + compId + " not found"));
        compilation.setPinned(pin);
        compilationRepository.save(compilation);
    }

}
