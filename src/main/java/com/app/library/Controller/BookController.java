package com.app.library.Controller;

import com.app.library.DTO.BookDTO;
import com.app.library.Service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("addBook/")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO dto) {
        dto.setIsDeleted(false);
        dto.setBookCode(null);
        BookDTO addedBook = bookService.addBook(dto);
        if (addedBook == null) {
            String message = "Cannot add Book!";
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    }

    @GetMapping("getBook/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO bookDTO = bookService.getBookById(id);
        if (bookDTO == null) {
            String message = MessageFormat.format("Book[{0}] cannot be found!", id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @GetMapping("searchBooks")
    public ResponseEntity<List<BookDTO>> getBookByCriteria(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String language
    ) {
        if (title == null && language == null && publisher == null) {
            String message = "At least one of the criteria must be provided";
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        List<BookDTO> bookDTOList = bookService.getBookByCriteria(title, language, publisher);

        return new ResponseEntity<>(bookDTOList, HttpStatus.OK);
    }

    @PatchMapping("updateBook/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO dto) {
        dto.setId(id);
        BookDTO updatedBook = bookService.updateBook(dto);

        if (updatedBook == null) {
            String message = MessageFormat.format("Book[{0}] cannot be found!", id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        int result = bookService.deleteBook(id);
        String message;
        HttpHeaders headers = new HttpHeaders();
        return switch (result) {
            case -1: {
                message = MessageFormat.format("Book[{0}] not found!", id);
                headers.add("error-message", message);
                yield new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
            }
            case 0: {
                message = MessageFormat.format("Book[{0}] already deleted!", id);
                headers.add("error-message", message);
                yield new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
            }
            case 1: {
                message = MessageFormat.format("Book[{0}] deleted successfully.", id);
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
}
