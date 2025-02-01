package com.app.library.Controller;

import com.app.library.DTO.AuthorDTO;
import com.app.library.Service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("api/author")
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("addAuthor")
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody AuthorDTO authorDTO) {
        authorDTO.setIsDeleted(false);
        AuthorDTO createdAuthorDTO = authorService.addAuthor(authorDTO);
        return new ResponseEntity<>(createdAuthorDTO, HttpStatus.CREATED);
    }

    @GetMapping("getAuthors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthor() {
        List<AuthorDTO> authorDTOList = authorService.getAuthors();
        return new ResponseEntity<>(authorDTOList, HttpStatus.OK);
    }

    @GetMapping("getAuthor/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Long id) {
        AuthorDTO authorDTO = authorService.getAuthor(id);
        if (authorDTO == null) {
            String message = MessageFormat.format("Author[{0}] not found.", id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @PatchMapping("updateAuthor/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDTO authorDTO) {
        authorDTO.setId(id);
        AuthorDTO updatedAuthorDTO = authorService.updateAuthor(authorDTO);
        return new ResponseEntity<>(updatedAuthorDTO, HttpStatus.OK);
    }

    @DeleteMapping("deleteAuthor/{id}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable("id") Long id) {
        int result = authorService.deleteAuthor(id);
        String message;
        HttpHeaders headers = new HttpHeaders();
        return switch (result) {
            case -1: {
                message = MessageFormat.format("Author[{0}] not found!", id);
                headers.add("error-message", message);
                yield new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
            }
            case 0: {
                message = MessageFormat.format("Author[{0}] already deleted!", id);
                headers.add("error-message", message);
                yield new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
            }
            case 1: {
                message = MessageFormat.format("Author[{0}] deleted successfully.", id);
                headers.add("success", message);
                yield new ResponseEntity<>(message, headers, HttpStatus.OK);
            }
            default: {
                message = "An error occurred during deletion.";
                headers.add("error-message", message);
                yield new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @GetMapping("search")
    public ResponseEntity<List<AuthorDTO>> searchAuthorByCriteria(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String nationality
    ) {
        if (firstname == null && lastname == null && nationality == null) {
            String message = "At least one of the criteria must be provided.";
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }
        List<AuthorDTO> authorDTOList = authorService.getAuthorByCriteria(firstname, lastname, nationality);

        authorDTOList.removeIf(AuthorDTO::getIsDeleted);

        return new ResponseEntity<>(authorDTOList, HttpStatus.OK);
    }
}
