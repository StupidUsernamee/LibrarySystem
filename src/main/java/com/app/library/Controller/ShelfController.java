package com.app.library.Controller;

import com.app.library.DTO.ShelfDTO;
import com.app.library.Service.ShelfService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

/**
 * REST Controller for managing shelves in the library system.
 */
@RestController
@RequestMapping("api/shelf")
@AllArgsConstructor
public class ShelfController {

    private final ShelfService shelfService;

    /**
     * Retrieves all shelves that are not marked as deleted.
     *
     * @return ResponseEntity containing a list of ShelfDTOs and HTTP status
     */
    @GetMapping("getShelves")
    public ResponseEntity<List<ShelfDTO>> getShelves() {
        List<ShelfDTO> shelfDTOList = shelfService.getAllShelves();
        return new ResponseEntity<>(shelfDTOList, HttpStatus.OK);
    }

    /**
     * Retrieves a specific shelf by its number.
     *
     * @param shelfNumber the number of the shelf to retrieve
     * @return ResponseEntity containing the ShelfDTO or an error message
     */
    @GetMapping("getShelf/{id}")
    public ResponseEntity<ShelfDTO> getShelf(@PathVariable("id") int shelfNumber) {
        ShelfDTO shelfDTO = shelfService.getShelvesByShelfNumber(shelfNumber);
        if (shelfDTO == null) {
            String message = MessageFormat.format("Shelf[{0}] not found.", shelfNumber);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shelfDTO, HttpStatus.OK);
    }

    /**
     * Adds a new shelf to the library system.
     *
     * @param shelfDTO the shelf data to be added
     * @return ResponseEntity containing the saved ShelfDTO or an error message
     */
    @PostMapping("addShelf")
    public ResponseEntity<ShelfDTO> addShelf(@RequestBody ShelfDTO shelfDTO) {
        if (shelfDTO == null) {
            String message = "ShelfDTO is null!";
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        // Ensure the shelf and its associated hall are marked as not deleted
        shelfDTO.setIsDeleted(false);
        if (shelfDTO.getHallDTO() != null) {
            shelfDTO.getHallDTO().setIsDeleted(false);
        }

        ShelfDTO savedShelfDTO = shelfService.addShelf(shelfDTO);
        return new ResponseEntity<>(savedShelfDTO, HttpStatus.CREATED);
    }

    /**
     * Updates an existing shelf in the library system.
     *
     * @param shelfDTO the updated shelf data
     * @param id       the shelf number to update
     * @return ResponseEntity containing the updated ShelfDTO or an error message
     */
    @PatchMapping("updateShelf/{id}")
    public ResponseEntity<ShelfDTO> updateShelf(@RequestBody ShelfDTO shelfDTO, @PathVariable("id") int id) {
        if (shelfDTO == null) {
            String message = "ShelfDTO is null!";
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        // Update shelf details
        shelfDTO.setShelfNumber(id);
        shelfDTO.setIsDeleted(false);
        if (shelfDTO.getHallDTO() != null) {
            shelfDTO.getHallDTO().setIsDeleted(false);
        }

        ShelfDTO updatedShelf = shelfService.updateShelf(shelfDTO);
        return new ResponseEntity<>(updatedShelf, HttpStatus.OK);
    }

    /**
     * Deletes a specific shelf by marking it as deleted.
     *
     * @param shelfNumber the number of the shelf to delete
     * @return ResponseEntity containing a success or error message
     */
    @DeleteMapping("deleteShelf/{id}")
    public ResponseEntity<String> deleteShelf(@PathVariable("id") int shelfNumber) {
        int result = shelfService.deleteShelf(shelfNumber);

        String message;
        return switch (result) {
            case 1 -> {
                message = MessageFormat.format("Shelf[{0}] successfully deleted.", shelfNumber);
                yield new ResponseEntity<>(message, HttpStatus.OK);
            }
            case 0, -1 -> {
                message = MessageFormat.format("Shelf[{0}] not found.", shelfNumber);
                yield new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            default -> {
                message = "An unexpected error occurred.";
                yield new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
