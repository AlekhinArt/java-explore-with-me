package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAllByInitiator(User user, Pageable pageable);

    @Query(value = "select e from Event e " +
            "where e.initiator in :initiators" +
            " and e.state in :states and" +
            " e.category in :categories and" +
            " e.eventDate between :eventDateStart and :eventDateEnd")
    Page<Event> findAllWithParam(@Param("initiators") List<User> initiators,
                                 @Param("states") List<State> states,
                                 @Param("categories") List<Category> categories,
                                 @Param("eventDateStart") LocalDateTime eventDateStart,
                                 @Param("eventDateEnd") LocalDateTime eventDateEnd, Pageable pageable);

    @Query(value = "select e " +
            "from Events e  left join " +
            "(where p.event_id, count(p.request_id) from participation_requests p " +
            "where p.status like 'CONFIRMED' group by p.event_id) i" +
            " on e.event_id = i.event_id" +
            "where (upper(e.description) like upper(:description) " +
            "or upper(e.annotation) like upper(:annotation)) " +
            "and e.category_id in :categories " +
            "and e.paid = :paid and e.event_date " +
            "and e.partisipant_limit> coalesce(i.count, 0) " +
            "between :eventDateStart and :eventDateEnd " +
            "order by e.event_date", nativeQuery = true)
    Page<Event> getEventsOnlyAvailable(@Param("description") String description,
                                       @Param("annotation") String annotation,
                                       @Param("categories") Collection<Category> categories,
                                       @Param("paid") boolean paid,
                                       @Param("eventDateStart") LocalDateTime eventDateStart,
                                       @Param("eventDateEnd") LocalDateTime eventDateEnd,
                                       Pageable pageable);

    @Query(value = "select * " +
            "from Events e  " +
            "where (upper(e.description) like upper(:description) " +
            "or upper(e.annotation) like upper(:annotation)) " +
            "and e.category_id in :categories " +
            "and e.paid = :paid and e.event_date " +
            "between :eventDateStart and :eventDateEnd " +
            "order by e.event_date", nativeQuery = true)
    Page<Event> getEvents(@Param("description") String description,
                          @Param("annotation") String annotation,
                          @Param("categories") Collection<Category> categories,
                          @Param("paid") boolean paid,
                          @Param("eventDateStart") LocalDateTime eventDateStart,
                          @Param("eventDateEnd") LocalDateTime eventDateEnd,
                          Pageable pageable);

    @Query("select e from Event e " +
            "where upper(e.annotation) like upper(:annotation) " +
            "or upper(e.description) like upper(:description) " +
            "and e.category in :categories and e.eventDate " +
            "between :eventDateStart and :eventDateEnd and e.paid = :paid")
    Page<Event> findByAnnotationLikeIgnoreCaseOrDescriptionLikeIgnoreCaseAndCategoryInAndEventDateBetweenAndPaid
            (@Param("annotation") String annotation,
             @Param("description") String description,
             @Param("categories") Collection<Category> categories,
             @Param("eventDateStart") LocalDateTime eventDateStart,
             @Param("eventDateEnd") LocalDateTime eventDateEnd,
             @Param("paid") boolean paid, Pageable pageable);

}
