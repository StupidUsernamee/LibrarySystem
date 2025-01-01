package com.app.library.Repository;

import com.app.library.Entity.Book_ageCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAgeCategoryRepository extends JpaRepository<Book_ageCategory, Long> {
}
