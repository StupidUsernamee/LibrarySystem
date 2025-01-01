package com.app.library.Repository;

import com.app.library.Entity.BookAgeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAgeCategoryRepository extends JpaRepository<BookAgeCategory, Long> {
    BookAgeCategory getBookAgeCategoryByTitleContainingIgnoreCase(String title);
}
