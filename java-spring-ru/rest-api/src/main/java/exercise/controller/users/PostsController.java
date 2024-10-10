package exercise.controller.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
                            @PathVariable int id) {
        List<Post> foundedPosts = new ArrayList<>();
        for (Post post : posts) {
            if (post.getUserId() == id) {
                foundedPosts.add(post);
            }
        }
        return foundedPosts;
    }

    // Создание нового поста, привязанного к пользователю по `id`. Код должен возвращать статус 201,
    // тело запроса должно содержать `slug`, `title` и `body`.
    // Обратите внимание, что `userId` берется из маршрута

    @PostMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post,
                       @PathVariable int id) {
        if (post.getUserId() == id) {
            posts.add(post);
            return post;
        }
        return null;
    }

}
// END
