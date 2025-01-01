package com.app.library.Repository;

import com.app.library.Entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    Hall findByHallNumber(Long hallNumber);
}
