package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;

// BEGIN
@RestController
@RequestMapping(path = "/posts")
public class PostsController {

   @Autowired
   private final PostRepository postRepository;
   @Autowired
   private final CommentRepository commentRepository;

    public PostsController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    // *GET /posts* — список всех постов +
    // *GET /posts/{id}* – просмотр конкретного поста +
    // *POST /posts* – создание нового поста. При успешном создании возвращается статус 201 +
    // *PUT /posts/{id}* – обновление поста +
    // *DELETE /posts/{id}* – удаление поста. При удалении поста удаляются все комментарии этого поста
    @GetMapping
    public List<Post> index() {
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post show(@PathVariable long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post with id " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post newPost) {
        return postRepository.save(newPost);
    }

    @PutMapping(path = "/{id}")
    public void update(@RequestBody Post post, @PathVariable long id) {
        var updatedPost = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post with id " + id + " not found"));
        updatedPost.setTitle(post.getTitle());
        updatedPost.setBody(post.getBody());
        postRepository.save(updatedPost);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        postRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }


}
// END
