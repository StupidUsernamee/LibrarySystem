package com.app.library.Service.Impl;

import com.app.library.DTO.HallDTO;
import com.app.library.DTO.LibraryDTO;
import com.app.library.Entity.Hall;
import com.app.library.Entity.Library;
import com.app.library.Mapper.HallMapper;
import com.app.library.Mapper.LibraryMapper;
import com.app.library.Repository.HallRepository;
import com.app.library.Repository.LibraryRepository;
import com.app.library.Service.HallService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;   // Repository for Hall entity
    private final LibraryRepository libraryRepository;   // Repository for Library entity

    /**
     * Creates a new Hall.
     * @param hallDTO Data Transfer Object representing Hall details
     * @return HallDTO of the created Hall
     */
    @Override
    public HallDTO createHall(HallDTO hallDTO) {
        // Handle Library data from HallDTO
        LibraryDTO libraryDTO = hallDTO.getLibraryDTO();
        if (libraryDTO == null) {
            throw new NullPointerException("libraryDTO cannot be null!");
        }

        // Map LibraryDTO to Library entity and save it to the database
        Library library = LibraryMapper.mapToLibraryEntity(libraryDTO);
        library = libraryRepository.save(library);

        // Map HallDTO to Hall entity
        Hall hall = HallMapper.mapToHallEntity(hallDTO);

        // Set the associated Library for the Hall entity
        hall.setLibrary(library);

        // Save the Hall entity to the database
        hall = hallRepository.save(hall);

        // Convert the saved Hall entity to HallDTO and return it
        return HallMapper.mapToHallDTO(hall);
    }

    /**
     * Retrieves all Halls, excluding the ones marked as deleted.
     * @return List of HallDTOs
     */
    @Override
    public List<HallDTO> getAllHalls() {
        // Fetch all Hall entities from the database
        List<Hall> halls = hallRepository.findAll();

        // Convert the list of Hall entities to a list of HallDTOs
        List<HallDTO> hallDTOList = new ArrayList<>(halls.stream().map(HallMapper::mapToHallDTO).toList());

        // Remove HallDTOs where isDeleted is true
        hallDTOList.removeIf(HallDTO::getIsDeleted);

        // Return the list of non-deleted HallDTOs
        return hallDTOList;
    }

    /**
     * Updates an existing Hall.
     * @param hallDTO Data Transfer Object containing updated Hall details
     * @return HallDTO of the updated Hall
     */
    @Override
    public HallDTO updateHall(HallDTO hallDTO) {
        // Find the Hall entity by its hallNumber
        Hall hallToUpdate = hallRepository.findByHallNumber(hallDTO.getHallNumber());

        // If the Hall is marked as deleted, return null
        if (checkIsDeleted(hallToUpdate)) {
            return null;
        }

        // Update the Hall entity with data from HallDTO
        updateHallEntityFromDTO(hallToUpdate, hallDTO);

        // Save the updated Hall entity and return its DTO
        Hall savedHall = hallRepository.save(hallToUpdate);
        return HallMapper.mapToHallDTO(savedHall);
    }

    /**
     * Checks if the Hall is marked as deleted.
     * @param hall Hall entity to check
     * @return true if deleted, otherwise false
     */
    private boolean checkIsDeleted(Hall hall) {
        return hall.getIsDeleted();
    }

    /**
     * Updates a Hall entity's fields from the provided HallDTO.
     * @param hall Hall entity to update
     * @param hallDTO HallDTO containing updated details
     */
    private void updateHallEntityFromDTO(Hall hall, HallDTO hallDTO) {
        if (hallDTO.getHallNumber() != null) {
            hall.setHallNumber(hallDTO.getHallNumber());
        }
        if (hallDTO.getCapacity() != null) {
            hall.setCapacity(hallDTO.getCapacity());
        }
    }

    /**
     * Marks a Hall as deleted by its hallNumber.
     * @param hallNumber The hallNumber of the Hall to delete
     * @return -1 if not found, 0 if already deleted, 1 if deleted successfully
     */
    @Override
    public int deleteHall(long hallNumber) {
        // Find the Hall entity by its hallNumber
        Hall hall = hallRepository.findByHallNumber(hallNumber);
        if (hall == null) {
            return -1;  // Not found
        } else if (hall.getIsDeleted()) {
            return 0;   // Already deleted
        }

        // Mark the Hall as deleted and save
        hall.setIsDeleted(true);
        hallRepository.save(hall);
        return 1;   // Deleted successfully
    }

    /**
     * Retrieves a Hall by its hallNumber.
     * @param hallNumber The hallNumber of the Hall to retrieve
     * @return HallDTO of the retrieved Hall, or null if not found
     */
    @Override
    public HallDTO getHallByNumber(Long hallNumber) {
        try {
            // Find the Hall entity by hallNumber and map it to HallDTO
            return HallMapper.mapToHallDTO(hallRepository.findByHallNumber(hallNumber));
        } catch (Exception e) {
            return null;  // Return null if an exception occurs
        }
    }
}
