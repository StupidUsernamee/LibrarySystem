package com.app.library.Service.Impl;

import com.app.library.DTO.RowDTO;
import com.app.library.DTO.ShelfDTO;
import com.app.library.Entity.Row;
import com.app.library.Entity.Shelf;
import com.app.library.Mapper.RowMapper;
import com.app.library.Mapper.ShelfMapper;
import com.app.library.Repository.RowRepository;
import com.app.library.Repository.ShelfRepository;
import com.app.library.Service.RowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for managing Rows.
 */
@Service
@AllArgsConstructor
public class RowServiceImpl implements RowService {

    private final RowRepository rowRepository;
    private final ShelfRepository shelfRepository;

    // Flag to allow updating the occupied capacity of a row
    private final boolean OccupiedRowCapacityToChange = true;

    /**
     * Retrieves all rows that are not marked as deleted.
     *
     * @return List of RowDTOs
     */
    @Override
    public List<RowDTO> getRows() {
        List<Row> rows = rowRepository.findAll();

        // Convert entities to DTOs
        List<RowDTO> rowDTOs = new ArrayList<>(rows.stream().map(RowMapper::mapToRowDTO).toList());

        // Remove rows marked as deleted
        rowDTOs.removeIf(RowDTO::getIsDeleted);

        return rowDTOs;
    }

    /**
     * Retrieves a row by its row number.
     *
     * @param rowNumber the unique number of the row
     * @return RowDTO if found, otherwise null
     */
    @Override
    public RowDTO getRowByRowNumber(int rowNumber) {
        try {
            return RowMapper.mapToRowDTO(rowRepository.findByRowNumber(rowNumber));
        } catch (NullPointerException e) {
            return null; // Handle cases where the row is not found
        }
    }

    /**
     * Adds a new row to the database.
     *
     * @param rowDTO the data transfer object representing the row
     * @return the saved RowDTO
     */
    @Override
    public RowDTO addRow(RowDTO rowDTO) {
        // Ensure the associated ShelfDTO is not null
        ShelfDTO shelfDTO = rowDTO.getShelfDTO();
        if (shelfDTO == null) {
            throw new NullPointerException("shelfDTO is null");
        }

        // Map ShelfDTO to entity and save
        Shelf shelf = ShelfMapper.mapToShelfEntity(shelfDTO);
        Shelf savedShelf = shelfRepository.save(shelf);

        // Map RowDTO to entity, associate it with the saved shelf, and save
        Row row = RowMapper.mapToRowEntity(rowDTO);
        row.setShelf(savedShelf);
        Row savedRow = rowRepository.save(row);

        // Map the saved entity back to a DTO and return
        return RowMapper.mapToRowDTO(savedRow);
    }

    /**
     * Updates an existing row in the database.
     *
     * @param rowDTO the data transfer object containing the updated row data
     * @return the updated RowDTO, or null if the row is marked as deleted
     */
    @Override
    public RowDTO updateRow(RowDTO rowDTO) {
        // Map RowDTO to entity
        Row rowToUpdate = rowRepository.findByRowNumber(rowDTO.getRowNumber());

        // Check if the row is marked as deleted
        if (checkIsDeleted(rowToUpdate)) {
            return null; // Cannot update a deleted row
        }

        // Update the entity with new data
        updateRowFromEntity(rowToUpdate, rowDTO);

        // Save the updated entity and map it back to a DTO
        Row savedRow = rowRepository.save(rowToUpdate);
        return RowMapper.mapToRowDTO(savedRow);
    }

    /**
     * Updates the row entity with new values from the DTO.
     *
     * @param row    the existing row entity to update
     * @param rowDTO the DTO containing updated values
     */
    private void updateRowFromEntity(Row row, RowDTO rowDTO) {
        if (rowDTO.getCapacity() != null) {
            row.setCapacity(rowDTO.getCapacity());
        }
        if (rowDTO.getOccupiedCapacity() != null && OccupiedRowCapacityToChange) {
            row.setOccupiedCapacity(rowDTO.getOccupiedCapacity());
        }
    }

    /**
     * Marks a row as deleted in the database.
     *
     * @param rowNumber the unique number of the row to delete
     * @return -1 if the row is not found, 0 if already deleted, 1 if deleted successfully
     */
    @Override
    public int deleteRow(int rowNumber) {
        // Find the row by its number
        Row row = rowRepository.findByRowNumber(rowNumber);
        if (row == null) {
            return -1; // Row not found
        }
        if (row.getIsDeleted()) {
            return 0; // Row already marked as deleted
        }

        // Mark the row as deleted and save
        row.setIsDeleted(true);
        rowRepository.save(row);
        return 1; // Successfully deleted
    }

    /**
     * Checks if a row is marked as deleted.
     *
     * @param row the row entity to check
     * @return true if the row is deleted, false otherwise
     */
    private boolean checkIsDeleted(Row row) {
        return row.getIsDeleted();
    }
}
