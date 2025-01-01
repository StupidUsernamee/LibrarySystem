package com.app.library.Repository;

import com.app.library.Entity.Book_status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStatusRepository extends JpaRepository<Book_status, Long> {
}
