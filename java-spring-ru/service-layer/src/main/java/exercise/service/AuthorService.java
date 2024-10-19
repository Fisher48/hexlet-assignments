package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;


    public List<AuthorDTO> getAll() {
        var authors = authorRepository.findAll();
        return authors.stream()
                .map(authorMapper::map)
                .toList();
    }

    public AuthorDTO create(AuthorCreateDTO createDTO) {
        var author = authorMapper.map(createDTO);
        author.setFirstName(createDTO.getFirstName());
        author.setLastName(createDTO.getLastName());
        authorRepository.save(author);
        return authorMapper.map(author);
    }

    public AuthorDTO findById(Long id) {
        var author = authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Author not found"));
        var authorDTO = authorMapper.map(author);
        return authorDTO;
    }

    public AuthorDTO update(Long id, AuthorUpdateDTO updateDTO) {
        var author = authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Author not found"));
        authorMapper.update(updateDTO, author);
        authorRepository.save(author);
        return authorMapper.map(author);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    // END
}
