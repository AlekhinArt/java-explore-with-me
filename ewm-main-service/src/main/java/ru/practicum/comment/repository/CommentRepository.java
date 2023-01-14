package ru.practicum.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.comment.model.Comment;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where upper(c.event.id) = upper(:id) order by c.creatTime DESC ")
    Collection<Comment> getComments(@Param("id") Long id, Pageable pageable);

    @Query("select c from Comment c where c.author.id = :id")
    Collection<Comment> findByAuthor_Id(@Param("id") Long id, Pageable pageable);

}
