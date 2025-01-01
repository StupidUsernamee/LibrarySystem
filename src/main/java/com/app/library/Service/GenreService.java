package com.app.library.Service;

import com.app.library.DTO.GenreDTO;

import java.util.List;

public interface GenreService {

    GenreDTO addGenre(String genreTitle);

    GenreDTO updateGenre(GenreDTO genre);

    GenreDTO getGenre(Long id);

    List<GenreDTO> getGenres();

    int deleteGenre(Long id);

    List<GenreDTO> getGenresByTitle(String genre);
}
