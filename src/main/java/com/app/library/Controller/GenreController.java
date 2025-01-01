package com.app.library.Controller;


import com.app.library.DTO.GenreDTO;
import com.app.library.Service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("api/genre")
@AllArgsConstructor
public class GenreController {

    private GenreService genreService;

    @PostMapping("addGenre")
    public ResponseEntity<GenreDTO> addGenre(@RequestBody String genreTitle) {
        GenreDTO genreDTO = genreService.addGenre(genreTitle);
        return new ResponseEntity<>(genreDTO, HttpStatus.OK);
    }

    @PatchMapping("updateGenre/{id}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable Long id, @RequestBody GenreDTO genreDTO) {
        genreDTO.setId(id);
        GenreDTO updatedGenreDTO = genreService.updateGenre(genreDTO);
        return new ResponseEntity<>(updatedGenreDTO, HttpStatus.OK);
    }

    @GetMapping("getGenre/{id}")
    public ResponseEntity<GenreDTO> getGenre(@PathVariable Long id) {
        GenreDTO genreDTO = genreService.getGenre(id);
        if (genreDTO != null) {
            return new ResponseEntity<>(genreDTO, HttpStatus.OK);
        }
        String message = MessageFormat.format("genre[{0}] not found", id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("error-message", message);
        return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
    }

    @GetMapping("getGenres")
    public ResponseEntity<List<GenreDTO>> getGenres() {
        return new ResponseEntity<>(genreService.getGenres(), HttpStatus.OK);
    }

    @DeleteMapping("deleteGenre/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Long id) {
        int result = genreService.deleteGenre(id);
        String message;
        return switch (result) {
            case 1: {
                message = MessageFormat.format("genre[{0}] deleted.", id);
                yield new ResponseEntity<>(message, HttpStatus.OK);
            }
            case 0: {
                message = MessageFormat.format("genre[{0}] already deleted.", id);
                yield new ResponseEntity<>(message, HttpStatus.OK);
            }
            case -1: {
                message = MessageFormat.format("genre[{0}] not found.", id);
                yield new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            default: {
                message = "An unexpected error occurred";
                yield new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @GetMapping("search")
    public ResponseEntity<List<GenreDTO>> searchGenre(@RequestParam String genre) {
        List<GenreDTO> genreDTOList = genreService.getGenresByTitle(genre);
        return new ResponseEntity<>(genreDTOList, HttpStatus.OK);
    }

}
