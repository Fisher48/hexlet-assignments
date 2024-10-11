package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping(path = "comments")
public class CommentsController {

    @Autowired
    private final CommentRepository commentRepository;

    public CommentsController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // *GET /comments* — список всех комментариев +
    // *GET /comments/{id}* – просмотр конкретного комментария +
    // *POST /comments* – создание нового комментария. При успешном создании возвращается статус 201 +
    // *PUT /comments/{id}* – обновление комментария +
    // *DELETE /comments/{id}* – удаление комментария

    @GetMapping
    public List<Comment> index() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment show(@PathVariable long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment newComment) {
        return commentRepository.save(newComment);
    }

    @PutMapping(path = "/{id}")
    public void update(@RequestBody Comment comment, @PathVariable long id) {
        var updateComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        updateComment.setBody(comment.getBody());
        commentRepository.save(updateComment);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        commentRepository.deleteById(id);
    }

}
// END
