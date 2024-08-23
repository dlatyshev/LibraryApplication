package org.dmytroqa.library.services.impl;

import org.dmytroqa.library.models.Book;
import org.dmytroqa.library.repository.BookRepository;
import org.dmytroqa.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooks() {
        return  bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> book = this.bookRepository.findById(id);
        return book.orElse(null);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            return null;
        }
        book.setId(id);
        this.bookRepository.save(book);
        return book;
    }

    @Override
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            this.bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Book addBook(Book newBook) {
        return bookRepository.save(newBook);
    }
}
