package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.StatsDto;
import ru.practicum.stats.model.Hit;
import ru.practicum.stats.repository.StatsRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public void addStats(Hit hit) {
        hit.setTime(LocalDateTime.now());
        statsRepository.save(hit);
    }

    @Override
    public Collection<StatsDto> getStats(String start, String end, Collection<String> uris, Boolean unique) {
        if (unique) {
            if (uris != null && !uris.isEmpty()) {
                return statsRepository.getAllWithUrisAndUnique(decoderDate(start), decoderDate(end), uris);
            } else return statsRepository.getAllWithUnique(decoderDate(start), decoderDate(end));
        }
        if (uris != null && !uris.isEmpty()) {
            return statsRepository.getAllWithUris(decoderDate(start), decoderDate(end), uris);
        }
        return statsRepository.getAll(decoderDate(start), decoderDate(end));

    }

    private LocalDateTime decoderDate(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(URLDecoder.decode(time, StandardCharsets.UTF_8), formatter);

    }
}

