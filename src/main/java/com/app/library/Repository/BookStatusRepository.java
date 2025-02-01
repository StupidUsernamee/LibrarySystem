package com.app.library.Repository;

import com.app.library.Entity.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStatusRepository extends JpaRepository<BookStatus, Long> {
}
