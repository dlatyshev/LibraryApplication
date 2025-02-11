package org.dmytroqa.library.controllers;

import jakarta.servlet.http.HttpSession;
import org.dmytroqa.library.models.Book;
import org.dmytroqa.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;
    private boolean loggedIn = false;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public RedirectView index(HttpSession session) {
        if (loggedIn) {
            return new RedirectView("index.html");
        } else {
            return new RedirectView("/login");
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        final String validUsername = "admin";
        final String validPassword = "admin";
        if (validUsername.equals(username) && validPassword.equals(password)) {
            this.loggedIn = true;
            return "redirect:/";
        }
        // If invalid, stay on the login page with an error message
        return "redirect:/login?error=true";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        this.loggedIn = false;
        return "login";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/book/{id}")
    public String getBookDetails(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "book";
    }

    // Update book
    @PostMapping("/book/{id}/update")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestParam String title,
                                           @RequestParam String description,
                                           @RequestParam String author,
                                           @RequestParam Integer yearPublished,
                                           @RequestParam String genre) {
        Book book = Book.builder()
                .id(id)
                .title(title)
                .description(description)
                .author(author)
                .yearPublished(yearPublished)
                .genre(genre)
                .build();
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    // Delete book
    @PostMapping("/book/{id}/delete")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBook(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestParam String title,
                                        @RequestParam String description,
                                        @RequestParam String author,
                                        @RequestParam Integer yearPublished,
                                        @RequestParam String genre) {
        Book newBook = Book.builder()
                .title(title)
                .description(description)
                .author(author)
                .yearPublished(yearPublished)
                .genre(genre)
                .build();
        Book savedBook = bookService.addBook(newBook);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
}
