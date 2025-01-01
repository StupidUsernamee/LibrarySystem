package com.app.library.Service.Impl;

import com.app.library.DTO.GenreDTO;
import com.app.library.Entity.Genre;
import com.app.library.Mapper.GenreMapper;
import com.app.library.Repository.GenreRepository;
import com.app.library.Service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public GenreDTO addGenre(String genreTitle) {
        Genre genre = new Genre();
        genre.setGenreTitle(genreTitle);
        genreRepository.save(genre);
        return GenreMapper.mapToGenreDTO(genre);
    }

    @Override
    public GenreDTO updateGenre(GenreDTO genreDTO) {
        Genre genreToUpdate = genreRepository.findById(genreDTO.getId()).get();

        if (genreDTO.getGenreTitle() != null) {
            genreToUpdate.setGenreTitle(genreDTO.getGenreTitle());
        }
        genreRepository.save(genreToUpdate);
        return GenreMapper.mapToGenreDTO(genreToUpdate);

    }

    @Override
    public GenreDTO getGenre(Long id) {
        try {
            return GenreMapper.mapToGenreDTO(genreRepository.findById(id).get());
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public List<GenreDTO> getGenres() {
        List<Genre> genres = genreRepository.findAll();

        List<GenreDTO> genresDTO = new ArrayList<>(genres.stream().map(GenreMapper::mapToGenreDTO).toList());

        genresDTO.removeIf(GenreDTO::getIsDeleted);

        return genresDTO;
    }

    @Override
    public int deleteGenre(Long id) {
        Genre genreToDelete;
        try {
            genreToDelete = genreRepository.findById(id).get();
        } catch (Exception e) {
            return -1;
        }

        if (genreToDelete.getIsDeleted()) {
            return 0;
        }
        genreToDelete.setIsDeleted(true);
        genreRepository.save(genreToDelete);
        return 1;
    }

    @Override
    public List<GenreDTO> getGenresByTitle(String genreTitle) {
        List<Genre> genreList = genreRepository.findGenreByGenreTitleContainingIgnoreCase(genreTitle);

        return new ArrayList<>(genreList.stream().map(GenreMapper::mapToGenreDTO).toList());
    }
}
