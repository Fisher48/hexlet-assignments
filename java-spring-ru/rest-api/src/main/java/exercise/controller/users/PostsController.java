package exercise.controller.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {

    private List<Post> posts = Data.getPosts();

    // список всех постов, написанных пользователем с таким же `userId`, как `id` в маршруте
    @GetMapping("/users/{id}/posts")
    public List<Post> index(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            @PathVariable("id") int id) {
        return posts.stream().skip((page - 1) * limit).limit(limit).filter(p -> p.getUserId() == id).toList();
    }

    // Создание нового поста, привязанного к пользователю по `id`. Код должен возвращать статус 201,
    // тело запроса должно содержать `slug`, `title` и `body`.
    // Обратите внимание, что `userId` берется из маршрута

    @PostMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post,
                       @PathVariable("id") int id) {
        post.setUserId(id);
        posts.add(post);
        return post;
    }

}
// END
