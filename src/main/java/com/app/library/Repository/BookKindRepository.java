package com.app.library.Repository;

import com.app.library.Entity.BookKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookKindRepository extends JpaRepository<BookKind, Long> {
    BookKind findByTitleContainingIgnoreCase(String title);

}
