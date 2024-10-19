package exercise.controller;

import java.util.List;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    // GET /books – просмотр списка всех книг
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> index() {
        return bookService.getAll();
    }

    // GET /books/{id} – просмотр конкретной книги
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO show(@PathVariable Long id) {
        return bookService.findById(id);
    }

    // POST /books – добавление новой книги.
    // При указании id несуществующего автора должен вернуться ответ с кодом 400 Bad request
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookCreateDTO createDTO) {
        return bookService.create(createDTO);
    }

    // PUT /books/{id} – редактирование книги.
    // При редактировании мы должны иметь возможность поменять название и автора.
    // При указании id несуществующего автора также должен вернуться ответ с кодом 400 Bad request
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO update(@PathVariable Long id,
                          @RequestBody @Valid BookUpdateDTO updateDTO) {
        return bookService.update(updateDTO, id);
    }

    // DELETE /books/{id} – удаление книги
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
    // END
}
