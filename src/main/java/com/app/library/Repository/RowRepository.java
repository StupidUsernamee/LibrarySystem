package com.app.library.Repository;

import com.app.library.Entity.Row;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowRepository extends JpaRepository<Row, Long> {
    Row findByRowNumber(Integer rowNumber);
}
