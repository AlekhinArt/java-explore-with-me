package ru.practicum.stats.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.StatsDto;
import ru.practicum.stats.mapper.StatsMapper;
import ru.practicum.stats.model.Hit;
import ru.practicum.stats.repository.StatsRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Collection;

@Service
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Autowired
    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public void addStats(HitDto hitDto) {
        Hit hit = StatsMapper.toHit(hitDto);
        hit.setTime(LocalDateTime.now());
        statsRepository.save(hit);
    }

    @Override
    public Collection<StatsDto> getStats(String start, String end, Collection<String> uris, Boolean unique) {
        log.info("get stats start {}, end: {}, uris {}, unique {}", start, end, uris, unique);
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
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                .optionalStart()
                .appendFraction(ChronoField.MICRO_OF_SECOND, 1, 9, true)
                .optionalEnd()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        return LocalDateTime.parse(URLDecoder.decode(time, StandardCharsets.UTF_8), dateTimeFormatter);

    }
}

