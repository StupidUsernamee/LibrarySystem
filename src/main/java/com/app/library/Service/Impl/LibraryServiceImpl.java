package com.app.library.Service.Impl;

import com.app.library.DTO.LibraryDTO;
import com.app.library.Entity.Library;
import com.app.library.Mapper.LibraryMapper;
import com.app.library.Repository.LibraryRepository;
import com.app.library.Service.LibraryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository; // Repository for CRUD operations on Library
    private final EntityManager entityManager; // For dynamic query creation using Criteria API

    @Override
    public LibraryDTO createLibrary(LibraryDTO libraryDTO) {
        // Convert DTO to entity
        Library library = LibraryMapper.mapToLibraryEntity(libraryDTO);

        // Save the entity in the database
        library = libraryRepository.save(library);

        // Convert saved entity back to DTO and return
        return LibraryMapper.mapToLibraryDTO(library);
    }

    @Override
    public List<LibraryDTO> getLibraries() {
        // Retrieve all library entities from the database
        List<Library> libraries = libraryRepository.findAll();

        // Convert entities to DTOs and collect them in a list
        List<LibraryDTO> libraryDTOList = new ArrayList<>(libraries.stream()
                .map(LibraryMapper::mapToLibraryDTO)
                .toList());

        // Remove libraries that are marked as deleted
        libraryDTOList.removeIf(LibraryDTO::getIsDeleted);

        return libraryDTOList;
    }

    /**
     *
     * @param number libraryNumber attribute
     * @return DTO of the entity for provided libraryNumber
     */
    @Override
    public LibraryDTO getLibraryByNumber(Long number) {
        // Retrieve library by its unique library number
        Library library = libraryRepository.findByLibraryNumber(number);

        // Return null if the library is marked as deleted
        if (checkIsDeleted(library)) {
            return null;
        }

        // Convert entity to DTO and return
        return LibraryMapper.mapToLibraryDTO(library);
    }

    @Override
    public Integer getLastLibraryNumber() {
        // Placeholder method to get the last library number (not implemented yet)
        return -1;
    }

    /**
     * performs partial updates on provided libraryDTO
     * @param libraryDTO DTO to be updated
     * @return updated entity in form of DTO
     */
    @Override
    public LibraryDTO updateLibrary(LibraryDTO libraryDTO) {
        // Retrieve library entity using its unique library number
        Library libraryToUpdate = libraryRepository.findByLibraryNumber(libraryDTO.getLibraryNumber());

        // Return null if the library is marked as deleted
        if (checkIsDeleted(libraryToUpdate)) {
            return null;
        }

        // Perform partial updates for non-null fields in the DTO
        updateLibraryEntityFromDTO(libraryToUpdate, libraryDTO);

        // Save the updated library entity
        Library savedLibrary = libraryRepository.save(libraryToUpdate);

        // Convert updated entity to DTO and return
        return LibraryMapper.mapToLibraryDTO(savedLibrary);
    }

    /**
     * Soft delete operation
     * @param libraryNumber libraryNumber attribute
     * @return status of deletion operation
     */
    @Override
    public int deleteLibrary(Long libraryNumber) {
        // Retrieve the library entity by its unique number
        Library library = libraryRepository.findByLibraryNumber(libraryNumber);

        // If the library is already marked as deleted, return 0 (failure)
        if (checkIsDeleted(library)) {
            return 0;
        }

        // Mark the library as deleted (soft delete)
        library.setIsDeleted(true);

        // Persist the change
        libraryRepository.flush();

        return 1; // Return 1 indicating successful deletion
    }


    /**
     * Performs search for libraries
     * Uses CriteriaBuilder to dynamically construct query for maximum flexibility
     * params that performs search: (at least one of them is not-null/non-empty)
     *      libraryName
     *      address
     *      libraryNumber
     * @return List of libraryDTOs that found by patterns provided
     **/
    @Override
    public List<LibraryDTO> getLibraryByCriteria(String libraryName, String address, Long libraryNumber) {
        // Use CriteriaBuilder to dynamically construct query
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Library> cq = cb.createQuery(Library.class);
        Root<Library> root = cq.from(Library.class);

        // Prepare list of predicates (query conditions)
        List<Predicate> predicates = new ArrayList<>();

        // Add 'like' condition for library name if provided
        if (libraryName != null && !libraryName.isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + libraryName + "%"));
        }

        // Add 'like' condition for address if provided
        if (address != null && !address.isEmpty()) {
            predicates.add(cb.like(root.get("address"), "%" + address + "%"));
        }

        // Add 'equal' condition for library number if provided
        if (libraryNumber != null) {
            predicates.add(cb.equal(root.get("libraryNumber"), libraryNumber));
        }

        // Apply all predicates to the query
        cq.where(predicates.toArray(new Predicate[0]));

        // Execute the query and retrieve matching libraries
        List<Library> libraries = entityManager.createQuery(cq).getResultList();

        // Convert entities to DTOs and return
        return libraries.stream()
                .map(LibraryMapper::mapToLibraryDTO)
                .toList();
    }

    /**
     * Checks if the given library entity is marked as deleted.
     * @param library the library entity to check
     * @return true if the library is deleted, false otherwise
     */
    private Boolean checkIsDeleted(Library library) {
        return (library.getIsDeleted() == Boolean.TRUE);
    }

    /**
     * Performs partial updates on a Library entity using a LibraryDTO.
     * Fields in the DTO that are non-null will update the entity's fields.
     * @param library the library entity to update
     * @param libraryDTO the DTO containing the updated values
     */
    private void updateLibraryEntityFromDTO(Library library, LibraryDTO libraryDTO) {
        if (libraryDTO.getLibraryNumber() != null) {
            library.setLibraryNumber(libraryDTO.getLibraryNumber());
        }
        if (libraryDTO.getName() != null) {
            library.setName(libraryDTO.getName());
        }
        if (libraryDTO.getAddress() != null) {
            library.setAddress(libraryDTO.getAddress());
        }
    }
}
