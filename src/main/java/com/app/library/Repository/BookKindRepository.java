package com.app.library.Repository;

import com.app.library.Entity.Book_Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookKindRepository extends JpaRepository<Book_Kind, Long> {
}
