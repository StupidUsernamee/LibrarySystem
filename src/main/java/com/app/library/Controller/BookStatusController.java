package com.app.library.Controller;


import com.app.library.DTO.BookStatusDTO;
import com.app.library.Service.BookStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/BStatus")
@AllArgsConstructor
public class BookStatusController {

    private final BookStatusService bookStatusService;

    @GetMapping("getStatus/{id}")
    public ResponseEntity<BookStatusDTO> getStatus(@PathVariable Long id) {
        BookStatusDTO bookStatusDTO = bookStatusService.getBookStatus(id);
        if (bookStatusDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(bookStatusDTO, HttpStatus.OK);
    }

    @GetMapping("getAllStatus")
    public ResponseEntity<List<BookStatusDTO>> getAllStatus() {
        return ResponseEntity.ok(bookStatusService.getAllBookStatus());
    }

    @PostMapping("addStatus")
    public ResponseEntity<BookStatusDTO> addStatus() {
        BookStatusDTO bookStatusDTO = bookStatusService.addBookStatus();
        return new ResponseEntity<>(bookStatusDTO, HttpStatus.CREATED);
    }

    @PatchMapping("updateStatus/{id}/available")
    public ResponseEntity<BookStatusDTO> markAvailableStatus(@PathVariable Long id) {
        BookStatusDTO bookStatusDTO = bookStatusService.markStatusAvailable(id);
        if (bookStatusDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(bookStatusDTO, HttpStatus.OK);
    }

    @PatchMapping("updateStatus/{id}/missing")
    public ResponseEntity<BookStatusDTO> markMissingStatus(@PathVariable Long id) {
        BookStatusDTO bookStatusDTO = bookStatusService.markStatusMissing(id);
        if (bookStatusDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(bookStatusDTO, HttpStatus.OK);
    }

    @PatchMapping("updateStatus/{id}/reserve")
    public ResponseEntity<BookStatusDTO> markReserveStatus(@PathVariable Long id) {
        BookStatusDTO bookStatusDTO = bookStatusService.markStatusReserved(id);
        if (bookStatusDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(bookStatusDTO, HttpStatus.OK);
    }

    @PatchMapping("updateStatus/{id}/borrow")
    public ResponseEntity<BookStatusDTO> markBorrowStatus(@PathVariable Long id) {
        BookStatusDTO bookStatusDTO = bookStatusService.markStatusBorrowed(id);
        if (bookStatusDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(bookStatusDTO, HttpStatus.OK);
    }

    @PatchMapping("updateStatus/{id}/request")
    public ResponseEntity<BookStatusDTO> markRequestStatus(@PathVariable Long id) {
        BookStatusDTO bookStatusDTO = bookStatusService.markStatusRequested(id);
        if (bookStatusDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(bookStatusDTO, HttpStatus.OK);
    }

    @DeleteMapping("deleteStatus/{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable Long id) {
        int result = bookStatusService.deleteBookStatus(id);

        return switch (result) {
            case 0: {
                yield new ResponseEntity<>("ALREADY DELETED", HttpStatus.BAD_REQUEST);
            }
            case 1: {
                yield new ResponseEntity<>("DELETED SUCCESSFULLY", HttpStatus.OK);
            }
            case -1: {
                yield new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
            }
            default: {
                yield new ResponseEntity<>("UNEXPECTED ERROR HAPPENED", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
