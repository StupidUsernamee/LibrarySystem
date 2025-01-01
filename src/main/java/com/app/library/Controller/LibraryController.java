package com.app.library.Controller;

import com.app.library.DTO.LibraryDTO;
import com.app.library.Service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing libraries in the system.
 */
@RestController
@RequestMapping("api/libraries") // Base URL for library-related endpoints
@AllArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    /**
     * Endpoint to create a new library.
     *
     * @param libraryDTO the library data to be added
     * @return ResponseEntity containing the created LibraryDTO and HTTP status
     */
    @PostMapping("addLibrary")
    public ResponseEntity<LibraryDTO> createLibrary(@RequestBody LibraryDTO libraryDTO) {
        libraryDTO.setIsDeleted(false); // Mark the new library as active
        LibraryDTO savedLibraryDTO = libraryService.createLibrary(libraryDTO);
        return new ResponseEntity<>(savedLibraryDTO, HttpStatus.CREATED); // HTTP 201 Created
    }

    /**
     * Endpoint to retrieve all libraries.
     * Excludes libraries marked as deleted.
     *
     * @return ResponseEntity containing a list of LibraryDTOs and HTTP status
     */
    @GetMapping("getLibraries")
    public ResponseEntity<List<LibraryDTO>> getAllLibraries() {
        List<LibraryDTO> allLibraries = libraryService.getLibraries();
        return new ResponseEntity<>(allLibraries, HttpStatus.OK); // HTTP 200 OK
    }

    /**
     * Endpoint to retrieve a library by its library number.
     *
     * @param libraryNumber the unique number of the library
     * @return ResponseEntity containing the LibraryDTO or an error response
     */
    @GetMapping("{libraryNumber}")
    public ResponseEntity<LibraryDTO> getLibraryByNumber(@PathVariable Long libraryNumber) {
        LibraryDTO libraryDTO = libraryService.getLibraryByNumber(libraryNumber);
        if (libraryDTO == null) {
            String message = String.format("Library with number [%d] not found.", libraryNumber);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error-Message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND); // HTTP 404 Not Found
        }
        return new ResponseEntity<>(libraryDTO, HttpStatus.OK); // HTTP 200 OK
    }

    /**
     * Endpoint to update an existing library's details.
     *
     * @param libraryDTO    the updated library data
     * @param libraryNumber the library number to update
     * @return ResponseEntity containing the updated LibraryDTO and HTTP status
     */
    @PatchMapping("updateLibrary/{libraryNumber}")
    public ResponseEntity<LibraryDTO> updateLibrary(@RequestBody LibraryDTO libraryDTO, @PathVariable Long libraryNumber) {
        libraryDTO.setLibraryNumber(libraryNumber);
        LibraryDTO updatedLibrary = libraryService.updateLibrary(libraryDTO);
        return new ResponseEntity<>(updatedLibrary, HttpStatus.OK); // HTTP 200 OK
    }

    /**
     * Endpoint to soft-delete a library by its ID (library number).
     *
     * @param libraryNumber the ID of the library to delete
     * @return ResponseEntity containing a success or error message
     */
    @DeleteMapping("deleteLibrary/{id}")
    public ResponseEntity<String> deleteLibrary(@PathVariable("id") Long libraryNumber) {
        int result = libraryService.deleteLibrary(libraryNumber);

        String message = switch (result) {
            case 1 -> "Library deleted successfully.";
            case 0 -> "Library not found.";
            default -> "An error occurred during deletion.";
        };

        HttpStatus status = (result == 1) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(message, status);
    }

    /**
     * Endpoint to search for libraries by name, address, or library number.
     *
     * @param name          optional name of the library
     * @param address       optional address of the library
     * @param libraryNumber optional unique number of the library
     * @return ResponseEntity containing a list of matching libraries or an error response
     */
    @GetMapping("search")
    public ResponseEntity<List<LibraryDTO>> searchLibraryByNameAddress(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Long libraryNumber) {

        // Validate that at least one search criterion is provided
        if (name == null && address == null && libraryNumber == null) {
            String message = "At least one search criterion must be provided.";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error-Message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST); // HTTP 400 Bad Request
        }

        // Perform search based on criteria
        List<LibraryDTO> libraries = libraryService.getLibraryByCriteria(address, name, libraryNumber);
        return new ResponseEntity<>(libraries, HttpStatus.OK); // HTTP 200 OK
    }
}
