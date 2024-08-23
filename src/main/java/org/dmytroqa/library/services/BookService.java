package org.dmytroqa.library.services;

import org.dmytroqa.library.models.Book;

import java.util.List;

public interface BookService {
    List<Book> findAllBooks();
    Book findById(Long id);
    Book updateBook(Long id, Book book);
    boolean deleteBook(Long id);
    Book addBook(Book newBook);
}
