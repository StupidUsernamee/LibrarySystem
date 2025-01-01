package com.app.library.Mapper;

import com.app.library.DTO.RowDTO;
import com.app.library.Entity.Row;
import lombok.Data;

@Data
public class RowMapper {

    public static RowDTO mapToRowDTO(Row row) {
        RowDTO rowDTO = new RowDTO();
        rowDTO.setId(row.getId());
        rowDTO.setRowNumber(row.getRowNumber());
        rowDTO.setCapacity(row.getCapacity());
        rowDTO.setOccupiedCapacity(row.getOccupiedCapacity());
        rowDTO.setShelfDTO(ShelfMapper.mapToShelfDTO(row.getShelf()));
        rowDTO.setIsDeleted(row.getIsDeleted());
        return rowDTO;
    }

    public static Row mapToRowEntity(RowDTO rowDTO) {
        Row row = new Row();
        row.setId(rowDTO.getId());
        row.setRowNumber(rowDTO.getRowNumber());
        row.setCapacity(rowDTO.getCapacity());
        row.setOccupiedCapacity(rowDTO.getOccupiedCapacity());
        row.setShelf(ShelfMapper.mapToShelfEntity(rowDTO.getShelfDTO()));
        row.setIsDeleted(rowDTO.getIsDeleted());
        return row;
    }
}
