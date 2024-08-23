package org.dmytroqa.library.repository;

import org.dmytroqa.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
}
