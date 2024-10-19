package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.BadRequestException;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorRepository authorRepository;


    public List<BookDTO> getAll() {
        var books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::map)
                .toList();
    }

    public BookDTO create(BookCreateDTO createDTO) {
        var book = bookMapper.map(createDTO);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public BookDTO findById(Long id) {
        var book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found"));
        return bookMapper.map(book);
    }

    public BookDTO update(BookUpdateDTO updateDTO, Long id) {
        var book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found"));
        authorRepository.findById(book.getAuthor().getId()).orElseThrow(
                () -> new BadRequestException("Bad request"));
        bookMapper.update(updateDTO, book);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public void delete(Long id) {
        var book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found"));
        bookRepository.delete(book);
    }

    // END
}
