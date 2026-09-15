package com.library.book;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {
    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Book> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody Book book) {
        if (repository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN already exists");
        }
        book.setAvailableCopies(book.getTotalCopies());
        return repository.save(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book incoming) {
        Book book = repository.findById(id).orElseThrow();
        int issued = book.getTotalCopies() - book.getAvailableCopies();
        if (incoming.getTotalCopies() < issued) {
            throw new IllegalArgumentException("Total copies cannot be below currently issued copies");
        }
        book.setTitle(incoming.getTitle());
        book.setAuthor(incoming.getAuthor());
        book.setIsbn(incoming.getIsbn());
        book.setTotalCopies(incoming.getTotalCopies());
        book.setAvailableCopies(incoming.getTotalCopies() - issued);
        return repository.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
