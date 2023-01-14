package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.ShowCommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.service.EventService;
import ru.practicum.exceptions.DataConflictException;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final UserService userService;


    @Override
    public ShowCommentDto createComment(Long userId, Long eventId, CommentDto commentDto) {
        Event event = eventService.getEventById(eventId);
        User user = userService.getById(userId);
        if (!(event.isComment())) {
            throw new DataConflictException("This event cannot be commented on");
        }
        return CommentMapper.toShowCommentDto(commentRepository.save(Comment.builder()
                .event(event)
                .author(user)
                .creatTime(LocalDateTime.now())
                .message(commentDto.getMessage())
                .build()));
    }

    @Override
    public ShowCommentDto deleteComment(Long commentId, Long userId) {
        Comment comment = getCommentFromRep(commentId);
        if (userId != null) {
            userService.getById(userId);
            if (!Objects.equals(comment.getAuthor().getId(), userId)) {
                log.debug("deleteComment.NotFoundException(Comment not found)");
                throw new NotFoundException("Comment not found");
            }
        }
        try {
            log.info("deleteComment id: {}", commentId);
            return CommentMapper.toShowCommentDto(comment);
        } catch (Exception e) {
            log.debug("deleteComment.NotFoundException(Comment not found)");
            throw new NotFoundException("Comment not found");
        }
    }

    @Override
    public Boolean changeAbilityToComment(Long userId, Long eventId) {
        Event event = eventService.getEventById(eventId);
        User user;
        if (userId != null) {
            user = userService.getById(userId);
            if (!Objects.equals(user.getId(), event.getInitiator().getId())) {
                log.debug("changeAbilityToComment.NotFoundException(Comment not found)");
                throw new NotFoundException("You cannot change the access modifier to a non-event creator");
            }
        }
        event.setComment(!event.isComment());
        eventRepository.save(event);
        return event.isComment();
    }

    @Override
    public ShowCommentDto getComment(Long commentId) {
        return CommentMapper.toShowCommentDto(commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("This comment not found")));

    }

    @Override
    public Collection<ShowCommentDto> getComments(Long eventId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        Event event = eventService.getEventById(eventId);
        return commentRepository.getComments(event.getId(), pageable).stream()
                .map(CommentMapper::toShowCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ShowCommentDto> getCommentsOneUser(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        userService.getById(userId);
        return commentRepository.findByAuthor_Id(userId, pageable).stream()
                .map(CommentMapper::toShowCommentDto)
                .collect(Collectors.toList());

    }

    public Comment getCommentFromRep(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("This comment not found"));
    }
}
