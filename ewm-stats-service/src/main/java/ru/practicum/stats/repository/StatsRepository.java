package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.dto.StatsDto;
import ru.practicum.stats.model.Hit;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsRepository extends JpaRepository<Hit, Long> {
    @Query("select new ru.practicum.stats.dto.StatsDto (s.app, s.uri, count(s.ip)) " +
            "from Hit s " +
            "where s.time between ?1 and ?2 group by s.app, s.uri order by s.app, s.uri")
    Collection<StatsDto> getAll(LocalDateTime timeStart, LocalDateTime timeEnd);

    @Query("select new ru.practicum.stats.dto.StatsDto ( s.app, s.uri, count(distinct s.ip)) " +
            "from Hit s " +
            "where s.time between ?1 and ?2 group by s.uri, s.uri order by s.app, s.uri")
    Collection<StatsDto> getAllWithUnique(LocalDateTime timeStart, LocalDateTime timeEnd);

    @Query("select new ru.practicum.stats.dto.StatsDto (app, uri, count (ip)) " +
            "from Hit  " +
            "where time between ?1 and ?2 and uri in ?3 group by app, uri order by app, uri")
    Collection<StatsDto> getAllWithUris(LocalDateTime timeStart, LocalDateTime timeEnd, Collection<String> uris);

    @Query("select new ru.practicum.stats.dto.StatsDto (s.app, s.uri, count(distinct s.ip)) " +
            "from Hit s " +
            "where s.time between ?1 and ?2 and s.uri in ?3 group by s.app, s.uri order by s.app, s.uri")
    Collection<StatsDto> getAllWithUrisAndUnique(LocalDateTime timeStart, LocalDateTime timeEnd, Collection<String> uris);


}
