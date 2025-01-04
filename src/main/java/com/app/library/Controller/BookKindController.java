package com.app.library.Controller;


import com.app.library.DTO.BookKindDTO;
import com.app.library.Service.BookKindService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("api/BKind")
@AllArgsConstructor
public class BookKindController {

    private final BookKindService bookKindService;

    @PostMapping("addBKind")
    public ResponseEntity<BookKindDTO> addBKind(@RequestBody String bookKindTitle) {
        BookKindDTO bookKindDTO = bookKindService.addBookKind(bookKindTitle);
        return new ResponseEntity<>(bookKindDTO, HttpStatus.OK);
    }


    @GetMapping("getBKind/{id}")
    public ResponseEntity<BookKindDTO> getBookKindById(@PathVariable Long id) {
        BookKindDTO bookKindDTO = bookKindService.getBookKindById(id);
        if (bookKindDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookKindDTO, HttpStatus.OK);
    }

    @GetMapping("getBKinds")
    public ResponseEntity<List<BookKindDTO>> getBookKinds() {
        List<BookKindDTO> bookKindDTOList = bookKindService.getAllBookKind();

        return new ResponseEntity<>(bookKindDTOList, HttpStatus.OK);
    }

    @GetMapping("getBKindTitle")
    public ResponseEntity<BookKindDTO> getBookKindTitle(@RequestParam String bookKindTitle) {
        BookKindDTO bookKindDTO = bookKindService.getBookKindByTitle(bookKindTitle);

        if (bookKindDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookKindDTO, HttpStatus.OK);
    }

    @PatchMapping("updateBKind/{id}")
    public ResponseEntity<BookKindDTO> updateBKind(@PathVariable Long id, @RequestBody BookKindDTO bookKindDTO) {
        bookKindDTO.setId(id);
        BookKindDTO bookKindToUpdate = bookKindService.updateBookKind(bookKindDTO);
        return new ResponseEntity<>(bookKindToUpdate, HttpStatus.OK);
    }

    @DeleteMapping("deleteBKind/{id}")
    public ResponseEntity<BookKindDTO> deleteBKind(@PathVariable Long id) {
        int result = bookKindService.deleteBookKind(id);

        HttpHeaders headers = new HttpHeaders();

        return switch (result) {
            case 0 :{
                headers.add("error-message", MessageFormat.format("BookKind with id {0} is already deleted.", id));
                yield new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
            }
            case 1: {
                headers.add(HttpHeaders.ACCEPT, MessageFormat.format("BookKind with id {0} deleted.", id));
                yield new ResponseEntity<>(headers, HttpStatus.OK);
            }
            case -1: {
                headers.add(HttpHeaders.ACCEPT, MessageFormat.format("BookKind with id {0} not found.", id));
                yield new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
            }
            default: {
                headers.add("error-message", "An unexpected error occurred.");
                yield new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
