package ru.practicum.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findAllByPinned(Pageable pageable, Boolean pinned);


    @Transactional
    @Modifying
    @Query(value = "delete from compilation_event where compilation_id = :compId and event_id = :eventId", nativeQuery = true)
    void deleteEventFromCompilation(Long compId, Long eventId);


    @Transactional
    @Modifying
    @Query(value = "insert into compilation_event(compilation_id, event_id) values (:compId, :eventId)", nativeQuery = true)
    void addEventToCompilation(Long compId, Long eventId);

}
