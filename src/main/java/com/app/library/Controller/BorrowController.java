package com.app.library.Controller;

import com.app.library.DTO.BorrowDTO;
import com.app.library.Service.BorrowService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/borrows")
@AllArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping("addBorrow")
    public ResponseEntity<BorrowDTO> addBorrow(BorrowDTO dto) {
        BorrowDTO borrowDTO = borrowService.addBorrow(dto);
        String message;
        HttpHeaders headers = new HttpHeaders();
        if (borrowDTO == null) {
            message = "User has borrowed maximum number of books," +
                    " please return at least one book first";
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(borrowDTO, HttpStatus.CREATED);
    }

    @GetMapping("getBorrow/{id}")
    public ResponseEntity<BorrowDTO> getBorrowById(@PathVariable("id") Long id) {
        BorrowDTO dto = borrowService.getBorrowById(id);
        String message;
        HttpHeaders headers = new HttpHeaders();
        if (dto == null) {
            message = MessageFormat.format("Borrow[{0}] not found.", id);
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("getAllBorrows")
    public ResponseEntity<List<BorrowDTO>> getAllBorrows() {
        List<BorrowDTO> borrowDTOList = borrowService.getAllBorrows();
        return new ResponseEntity<>(borrowDTOList, HttpStatus.OK);
    }

    @PatchMapping("returnBook/{id}")
    public ResponseEntity<String> returnBorrowedBook(@PathVariable("id") Long id) {
        String result = borrowService.returnBorrowedBook(id);
        String message;
        HttpHeaders headers = new HttpHeaders();
        if (Objects.equals(result, "failed1")) {
            message = MessageFormat.format("Borrow[{0}] does not exist.", id);
            headers.add("error-message", message);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
        if (Objects.equals(result, "failed2")) {
            message = MessageFormat.format("Borrow[{0}] is already returned", id);
            headers.add("error-message", message);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }
        message = MessageFormat.format("Borrow[{0}] returned successfully.", id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
