package com.app.library.Service.Impl;

import com.app.library.DTO.BookAgeCategoryDTO;
import com.app.library.Entity.BookAgeCategory;
import com.app.library.Mapper.BookAgeCategoryMapper;
import com.app.library.Repository.BookAgeCategoryRepository;
import com.app.library.Service.BookAgeCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the BookAgeCategoryService interface.
 * Provides business logic for managing book age categories.
 */
@Service
@AllArgsConstructor
public class BookAgeCategoryServiceImpl implements BookAgeCategoryService {

    private final BookAgeCategoryRepository bookAgeCategoryRepository;

    /**
     * Adds a new BookAgeCategory to the repository.
     *
     * @param bookAgeCategoryDTO the category details
     * @return the created category as a DTO
     */
    @Override
    public BookAgeCategoryDTO addBookAgeCategory(BookAgeCategoryDTO bookAgeCategoryDTO) {
        BookAgeCategory bookAgeCategory = BookAgeCategoryMapper.mapToAgeCategoryEntity(bookAgeCategoryDTO);
        bookAgeCategoryRepository.save(bookAgeCategory);
        return BookAgeCategoryMapper.mapToBookAgeCategoryDTO(bookAgeCategory);
    }

    /**
     * Retrieves a BookAgeCategory by its ID.
     *
     * @param id the category ID
     * @return the category details as a DTO, or null if not found
     */
    @Override
    public BookAgeCategoryDTO getBookAgeCategoryById(Long id) {
        Optional<BookAgeCategory> optionalCategory = bookAgeCategoryRepository.findById(id);
        return optionalCategory.map(BookAgeCategoryMapper::mapToBookAgeCategoryDTO).orElse(null);
    }

    /**
     * Retrieves all BookAgeCategories that are not marked as deleted.
     *
     * @return a list of all active categories
     */
    @Override
    public List<BookAgeCategoryDTO> getAllBookAgeCategory() {
        return bookAgeCategoryRepository.findAll().stream()
                .filter(category -> !category.getIsDeleted()) // Exclude deleted categories
                .map(BookAgeCategoryMapper::mapToBookAgeCategoryDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a BookAgeCategory by its title (case-insensitive, partial match).
     *
     * @param title the category title
     * @return the matching category as a DTO
     */
    @Override
    public BookAgeCategoryDTO getBookAgeCategoryByTitle(String title) {
        BookAgeCategory category = bookAgeCategoryRepository.getBookAgeCategoryByTitleContainingIgnoreCase(title);
        return category != null ? BookAgeCategoryMapper.mapToBookAgeCategoryDTO(category) : null;
    }

    /**
     * Updates an existing BookAgeCategory with new details.
     *
     * @param bookAgeCategoryDTO the new details
     * @return the updated category as a DTO
     */
    @Override
    public BookAgeCategoryDTO updateBookAgeCategory(BookAgeCategoryDTO bookAgeCategoryDTO) {
        Optional<BookAgeCategory> optionalCategory = bookAgeCategoryRepository.findById(bookAgeCategoryDTO.getId());
        if (optionalCategory.isEmpty()) {
            return null; // Return null or throw a custom exception if not found
        }

        BookAgeCategory bookAgeCategory = optionalCategory.get();
        updateEntityFromDTO(bookAgeCategoryDTO, bookAgeCategory);
        bookAgeCategoryRepository.save(bookAgeCategory);

        return BookAgeCategoryMapper.mapToBookAgeCategoryDTO(bookAgeCategory);
    }

    /**
     * Helper method to update an entity with data from a DTO.
     *
     * @param bookAgeCategoryDTO the source DTO
     * @param bookAgeCategory    the target entity
     */
    private void updateEntityFromDTO(BookAgeCategoryDTO bookAgeCategoryDTO, BookAgeCategory bookAgeCategory) {
        if (bookAgeCategoryDTO.getEndAge() != null) {
            bookAgeCategory.setEndAge(bookAgeCategoryDTO.getEndAge());
        }
        if (bookAgeCategoryDTO.getStartAge() != null) {
            bookAgeCategory.setStartAge(bookAgeCategoryDTO.getStartAge());
        }
        if (bookAgeCategoryDTO.getTitle() != null) {
            bookAgeCategory.setTitle(bookAgeCategoryDTO.getTitle());
        }
    }

    /**
     * Marks a BookAgeCategory as deleted.
     *
     * @param id the category ID
     * @return 1 if successfully deleted, 0 if already deleted, -1 if not found
     */
    @Override
    public int deleteBookAgeCategory(Long id) {
        Optional<BookAgeCategory> optionalCategory = bookAgeCategoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return -1; // Not found
        }

        BookAgeCategory bookAgeCategory = optionalCategory.get();
        if (bookAgeCategory.getIsDeleted()) {
            return 0; // Already deleted
        }

        bookAgeCategory.setIsDeleted(true);
        bookAgeCategoryRepository.save(bookAgeCategory);
        return 1; // Successfully deleted
    }
}
