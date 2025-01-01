package com.app.library.Repository;

import com.app.library.Entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    Shelf findByShelfNumber(int ShelfNumber);
}
