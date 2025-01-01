package com.app.library.Mapper;

import com.app.library.DTO.GenreDTO;
import com.app.library.Entity.Genre;
import lombok.Data;

@Data
public class GenreMapper {

    public static Genre mapToGenreEntity(GenreDTO genreDTO) {
        Genre genre = new Genre();
        genre.setGenreTitle(genreDTO.getGenreTitle());
        genre.setId(genreDTO.getId());
        genre.setIsDeleted(genreDTO.getIsDeleted());
        return genre;
    }

    public static GenreDTO mapToGenreDTO(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setGenreTitle(genre.getGenreTitle());
        genreDTO.setId(genre.getId());
        genreDTO.setIsDeleted(genre.getIsDeleted());
        return genreDTO;
    }
}
