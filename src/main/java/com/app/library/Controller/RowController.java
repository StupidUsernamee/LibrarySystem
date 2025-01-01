package com.app.library.Controller;

import com.app.library.DTO.RowDTO;
import com.app.library.Service.RowService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

/**
 * REST Controller for managing Rows.
 */
@RestController
@RequestMapping("api/rows")
@AllArgsConstructor
public class RowController {

    private final RowService rowService;

    /**
     * Retrieves all rows that are not marked as deleted.
     *
     * @return ResponseEntity containing a list of RowDTOs and HTTP status
     */
    @GetMapping("getRows")
    public ResponseEntity<List<RowDTO>> getAllRows() {
        List<RowDTO> rows = rowService.getRows();
        return new ResponseEntity<>(rows, HttpStatus.OK);
    }

    /**
     * Retrieves a specific row by its row number.
     *
     * @param rowNumber the row number to retrieve
     * @return ResponseEntity containing the RowDTO or an error message
     */
    @GetMapping("getRow/{id}")
    public ResponseEntity<RowDTO> getRow(@PathVariable("id") int rowNumber) {
        RowDTO rowDTO = rowService.getRowByRowNumber(rowNumber);

        if (rowDTO == null) {
            String message = MessageFormat.format("Row number[{0}] not found", rowNumber);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rowDTO, HttpStatus.OK);
    }

    /**
     * Adds a new row to the database.
     *
     * @param rowDTO the row data to be added
     * @return ResponseEntity containing the saved RowDTO or an error message
     */
    @PostMapping("addRow")
    public ResponseEntity<RowDTO> addRow(@RequestBody RowDTO rowDTO) {
        if (rowDTO == null || rowDTO.getShelfDTO() == null) {
            String message = "row/shelf object is null";
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        // Ensure row and shelf are marked as not deleted
        rowDTO.setIsDeleted(false);
        rowDTO.getShelfDTO().setIsDeleted(false);

        RowDTO savedRow = rowService.addRow(rowDTO);
        return new ResponseEntity<>(savedRow, HttpStatus.CREATED);
    }

    /**
     * Updates an existing row in the database.
     *
     * @param rowNumber the row number to update
     * @param rowDTO    the updated row data
     * @return ResponseEntity containing the updated RowDTO or an error message
     */
    @PatchMapping("updateRow/{id}")
    public ResponseEntity<RowDTO> updateRow(@PathVariable("id") int rowNumber, @RequestBody RowDTO rowDTO) {
        if (rowDTO == null) {
            String message = "row object is null";
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        // Associate the row number with the DTO
        rowDTO.setRowNumber(rowNumber);

        RowDTO updatedRow = rowService.updateRow(rowDTO);
        if (updatedRow == null) {
            String message = MessageFormat.format("Row number[{0}] cannot be updated (might be deleted).", rowNumber);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedRow, HttpStatus.OK);
    }

    /**
     * Deletes a specific row by marking it as deleted.
     *
     * @param rowNumber the row number to delete
     * @return ResponseEntity containing a success or error message
     */
    @DeleteMapping("deleteRow/{id}")
    public ResponseEntity<String> deleteRow(@PathVariable("id") int rowNumber) {
        int result = rowService.deleteRow(rowNumber);

        String message;
        return switch (result) {
            case -1 -> {
                message = MessageFormat.format("Row number[{0}] not found.", rowNumber);
                yield new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            case 0 -> {
                message = MessageFormat.format("Row number[{0}] is already deleted.", rowNumber);
                yield new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            case 1 -> {
                message = MessageFormat.format("Row number[{0}] successfully deleted.", rowNumber);
                yield new ResponseEntity<>(message, HttpStatus.OK);
            }
            default -> {
                message = "An unexpected error occurred.";
                yield new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
