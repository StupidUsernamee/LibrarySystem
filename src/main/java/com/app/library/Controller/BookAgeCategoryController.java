package com.app.library.Controller;

import com.app.library.DTO.BookAgeCategoryDTO;
import com.app.library.Service.BookAgeCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

/**
 * REST controller for managing Book Age Categories.
 */
@RestController
@RequestMapping("api/ageCategory") // Base URL for all endpoints in this controller
@AllArgsConstructor
public class BookAgeCategoryController {

    private final BookAgeCategoryService bookAgeCategoryService;

    /**
     * Adds a new BookAgeCategory.
     *
     * @param bookAgeCategoryDTO the category details
     * @return the created category with HTTP 201 status
     */
    @PostMapping("addCategory")
    public ResponseEntity<BookAgeCategoryDTO> addCategory(@RequestBody BookAgeCategoryDTO bookAgeCategoryDTO) {
        if (bookAgeCategoryDTO == null) {
            String message = "bookAgeCategoryDTO cannot be null.";
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        BookAgeCategoryDTO createdCategory = bookAgeCategoryService.addBookAgeCategory(bookAgeCategoryDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/api/ageCategory/getCategory/" + createdCategory.getId());
        return new ResponseEntity<>(createdCategory, headers, HttpStatus.CREATED);
    }

    /**
     * Retrieves a BookAgeCategory by its ID.
     *
     * @param id the category ID
     * @return the category details or an error response if not found
     */
    @GetMapping("getCategory/{id}")
    public ResponseEntity<BookAgeCategoryDTO> getCategoryById(@PathVariable long id) {
        BookAgeCategoryDTO category = bookAgeCategoryService.getBookAgeCategoryById(id);
        if (category == null) {
            String message = MessageFormat.format("BookAgeCategory with id {0} not found.", id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * Retrieves all BookAgeCategories.
     *
     * @return the list of all categories
     */
    @GetMapping("getAllCategories")
    public ResponseEntity<List<BookAgeCategoryDTO>> getAllCategories() {
        List<BookAgeCategoryDTO> categories = bookAgeCategoryService.getAllBookAgeCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    /**
     * Searches for a BookAgeCategory by title.
     *
     * @param title the category title
     * @return the matching category or HTTP 404 if not found
     */
    @GetMapping("search")
    public ResponseEntity<BookAgeCategoryDTO> search(@RequestParam String title) {
        BookAgeCategoryDTO category = bookAgeCategoryService.getBookAgeCategoryByTitle(title);
        if (category == null) {
            String message = MessageFormat.format("BookAgeCategory with title \"{0}\" not found.", title);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * Updates an existing BookAgeCategory.
     *
     * @param id                the category ID
     * @param bookAgeCategoryDTO the new details
     * @return the updated category
     */
    @PatchMapping("updateCategory/{id}")
    public ResponseEntity<BookAgeCategoryDTO> updateCategory(@PathVariable long id, @RequestBody BookAgeCategoryDTO bookAgeCategoryDTO) {
        bookAgeCategoryDTO.setId(id);
        BookAgeCategoryDTO updatedCategory = bookAgeCategoryService.updateBookAgeCategory(bookAgeCategoryDTO);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    /**
     * Deletes a BookAgeCategory by ID.
     *
     * @param id the category ID
     * @return the deletion status
     */
    @DeleteMapping("deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        int result = bookAgeCategoryService.deleteBookAgeCategory(id);
        HttpHeaders headers = new HttpHeaders();
        return switch (result) {
            case 1 -> {
                headers.add(HttpHeaders.ACCEPT, MessageFormat.format("BookAgeCategory with id {0} deleted.", id));
                yield new ResponseEntity<>(headers, HttpStatus.OK);
            }
            case 0 -> {
                headers.add("error-message", MessageFormat.format("BookAgeCategory with id {0} is already deleted.", id));
                yield new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
            }
            case -1 -> {
                headers.add(HttpHeaders.ACCEPT, MessageFormat.format("BookAgeCategory with id {0} not found.", id));
                yield new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
            }
            default -> {
                headers.add("error-message", "An unexpected error occurred.");
                yield new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
