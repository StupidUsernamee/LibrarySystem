package com.app.library.Controller;

import com.app.library.DTO.HallDTO;
import com.app.library.Service.HallService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/halls")
public class HallController {

    private final HallService hallService; // Service layer dependency for Hall operations

    /**
     * Adds a new Hall.
     * URL: http://localhost:8080/api/halls/addHall
     * @param hallDTO Hall data transfer object
     * @return ResponseEntity containing the created HallDTO and HTTP status
     */
    @PostMapping("addHall")
    public ResponseEntity<HallDTO> addHall(@RequestBody HallDTO hallDTO) {
        // Ensure the new Hall and associated Library are marked as not deleted
        hallDTO.setIsDeleted(false);
        hallDTO.getLibraryDTO().setIsDeleted(false);

        // Save the Hall and return the response
        HallDTO savedHallDTO = hallService.createHall(hallDTO);
        return new ResponseEntity<>(savedHallDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves all Halls.
     * @return ResponseEntity containing a list of HallDTOs and HTTP status
     */
    @GetMapping("/getHalls")
    public ResponseEntity<List<HallDTO>> getHalls() {
        // Get the list of all non-deleted Halls
        List<HallDTO> hallDTOList = hallService.getAllHalls();
        return new ResponseEntity<>(hallDTOList, HttpStatus.OK);
    }

    /**
     * Retrieves a Hall by its ID.
     * @param hallNumber The ID of the Hall to retrieve
     * @return ResponseEntity containing the HallDTO and HTTP status, or an error message if not found
     */
    @GetMapping("getHall/{id}")
    public ResponseEntity<HallDTO> getHall(@PathVariable("id") Long hallNumber) {
        // Fetch the Hall by its ID
        HallDTO hallDTO = hallService.getHallByNumber(hallNumber);

        // If not found, return a 404 response with an error message
        if (hallDTO == null) {
            String message = MessageFormat.format("Hall[{0}] not found.", hallNumber);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        // Return the found HallDTO with a 200 status
        return new ResponseEntity<>(hallDTO, HttpStatus.OK);
    }

    /**
     * Updates an existing Hall.
     * @param hallDTO Data transfer object containing updated Hall details
     * @param hallNumber The ID of the Hall to update
     * @return ResponseEntity containing the updated HallDTO and HTTP status
     */
    @PatchMapping("updateHall/{id}")
    public ResponseEntity<HallDTO> updateHall(@RequestBody HallDTO hallDTO, @PathVariable("id") Long hallNumber) {
        // Set the hallNumber from the path variable
        hallDTO.setHallNumber(hallNumber);

        // Update the Hall and return the response
        HallDTO savedHallDTO = hallService.updateHall(hallDTO);
        return new ResponseEntity<>(savedHallDTO, HttpStatus.OK);
    }

    /**
     * Marks a Hall as deleted by its ID.
     * @param hallNumber The ID of the Hall to delete
     * @return ResponseEntity containing a message and HTTP status based on the result
     */
    @DeleteMapping("deleteHall/{id}")
    public ResponseEntity<String> deleteHall(@PathVariable("id") Long hallNumber) {
        // Delete the Hall and handle the result
        int result = hallService.deleteHall(hallNumber);
        String message;

        // Return an appropriate response based on the result
        return switch (result) {
            case 0 -> {
                message = MessageFormat.format("Hall[{0}] not found!", hallNumber);
                yield new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            case 1 -> {
                message = MessageFormat.format("Hall[{0}] deleted.", hallNumber);
                yield new ResponseEntity<>(message, HttpStatus.OK);
            }
            case -1 -> {
                message = MessageFormat.format("Hall[{0}] not found.", hallNumber);
                yield new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            default -> {
                message = "An unexpected error occurred";
                yield new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
        };
    }
}
