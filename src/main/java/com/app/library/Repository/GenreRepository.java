package com.app.library.Repository;

import com.app.library.Entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findGenreByGenreTitleContainingIgnoreCase(String genreName);

    Genre findById(long id);
}
