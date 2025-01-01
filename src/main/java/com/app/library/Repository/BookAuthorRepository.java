package com.app.library.Repository;

import com.app.library.Entity.Book_Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookAuthorRepository extends JpaRepository<Book_Author, Long> {
}
