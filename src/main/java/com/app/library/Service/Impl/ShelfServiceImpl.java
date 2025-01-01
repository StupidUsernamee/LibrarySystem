package com.app.library.Service.Impl;

import com.app.library.DTO.HallDTO;
import com.app.library.DTO.ShelfDTO;
import com.app.library.Entity.Hall;
import com.app.library.Entity.Shelf;
import com.app.library.Mapper.ShelfMapper;
import com.app.library.Mapper.HallMapper;
import com.app.library.Repository.HallRepository;
import com.app.library.Repository.ShelfRepository;
import com.app.library.Service.ShelfService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShelfServiceImpl implements ShelfService {

    private final HallRepository hallRepository; // Repository for Hall operations
    private final ShelfRepository shelfRepository; // Repository for Shelf operations
    private final boolean OccupiedShelfCapacityToChange = true; // Flag to allow updating occupied capacity
    /**
     * Retrieves all non-deleted shelves.
     *
     * @return List of ShelfDTOs
     */
    @Override
    public List<ShelfDTO> getAllShelves() {
        // Fetch all shelves and map them to DTOs
        List<Shelf> shelfList = shelfRepository.findAll();
        List<ShelfDTO> shelfDTOList = new ArrayList<>(shelfList.stream()
                .map(ShelfMapper::mapToShelfDTO)
                .toList());

        // Remove deleted shelves from the result
        shelfDTOList.removeIf(ShelfDTO::getIsDeleted);

        return shelfDTOList;
    }

    /**
     * Retrieves a shelf by its shelf number.
     *
     * @param ShelfNumber The shelf number to find
     * @return ShelfDTO if found, null otherwise
     */
    @Override
    public ShelfDTO getShelvesByShelfNumber(int ShelfNumber) {
        try {
            // Fetch and map shelf entity to DTO
            return ShelfMapper.mapToShelfDTO(shelfRepository.findByShelfNumber(ShelfNumber));
        } catch (Exception e) {
            return null; // Return null if shelf not found or any exception occurs
        }
    }

    /**
     * Adds a new shelf.
     *
     * @param shelfDTO Shelf data transfer object
     * @return ShelfDTO representing the added shelf
     */
    @Override
    public ShelfDTO addShelf(ShelfDTO shelfDTO) {
        // Validate HallDTO
        HallDTO hallDTO = shelfDTO.getHallDTO();
        if (hallDTO == null) {
            throw new NullPointerException("hallDTO cannot be null");
        }

        // Map and save the Hall entity
        Hall hall = HallMapper.mapToHallEntity(hallDTO);
        Hall savedHall = hallRepository.save(hall);

        // Map ShelfDTO to entity and associate it with the saved Hall
        Shelf shelf = ShelfMapper.mapToShelfEntity(shelfDTO);
        shelf.setHall(savedHall);

        // Save the Shelf and map back to DTO
        Shelf savedShelf = shelfRepository.save(shelf);
        return ShelfMapper.mapToShelfDTO(savedShelf);
    }

    /**
     * Updates an existing shelf.
     *
     * @param shelfDTO Shelf data transfer object
     * @return ShelfDTO representing the updated shelf, or null if not updated
     */
    @Override
    public ShelfDTO updateShelf(ShelfDTO shelfDTO) {
        // Map DTO to entity
        Shelf shelfToUpdate = ShelfMapper.mapToShelfEntity(shelfDTO);

        // Check if the shelf is marked as deleted
        if (checkIsDeleted(shelfToUpdate)) {
            return null; // Return null if the shelf is deleted
        }

        // Update the shelf entity with values from the DTO
        updateShelfEntityFromDTO(shelfToUpdate, shelfDTO);

        // Save the updated shelf and return as DTO
        Shelf savedShelf = shelfRepository.save(shelfToUpdate);
        return ShelfMapper.mapToShelfDTO(savedShelf);
    }

    /**
     * Updates the Shelf entity based on DTO values.
     *
     * @param shelfToUpdate The Shelf entity to update
     * @param shelfDTO The DTO containing updated values
     */
    private void updateShelfEntityFromDTO(Shelf shelfToUpdate, ShelfDTO shelfDTO) {

        if (shelfDTO.getShelfCapacity() != null) {
            shelfToUpdate.setShelfCapacity(shelfDTO.getShelfCapacity());
        }
        if (shelfDTO.getOccupiedShelfCapacity() != null && OccupiedShelfCapacityToChange) {
            shelfToUpdate.setOccupiedShelfCapacity(shelfDTO.getOccupiedShelfCapacity());
        }
    }

    /**
     * Deletes a shelf by its shelf number (soft delete).
     *
     * @param ShelfNumber The shelf number to delete
     * @return 1 if deleted successfully, 0 if already deleted, -1 if not found
     */
    @Override
    public int deleteShelf(int ShelfNumber) {
        // Find the Shelf by its number
        Shelf shelf = shelfRepository.findByShelfNumber(ShelfNumber);
        if (shelf == null) {
            return -1; // Shelf not found
        } else if (shelf.getIsDeleted()) {
            return 0; // Shelf already deleted
        }

        // Mark the Shelf as deleted and save
        shelf.setIsDeleted(true);
        shelfRepository.save(shelf);
        return 1; // Deleted successfully
    }

    /**
     * Checks if a shelf is marked as deleted.
     *
     * @param shelf The Shelf entity to check
     * @return true if deleted, false otherwise
     */
    private Boolean checkIsDeleted(Shelf shelf) {
        return shelf.getIsDeleted();
    }
}
