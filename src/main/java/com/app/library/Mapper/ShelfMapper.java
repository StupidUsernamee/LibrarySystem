package com.app.library.Mapper;

import com.app.library.DTO.ShelfDTO;
import com.app.library.Entity.Shelf;
import lombok.Data;

@Data
public class ShelfMapper {

    public static ShelfDTO mapToShelfDTO(Shelf shelf) {
        ShelfDTO shelfDTO = new ShelfDTO();
        shelfDTO.setId(shelf.getId());
        shelfDTO.setShelfNumber(shelf.getShelfNumber());
        shelfDTO.setHallDTO(HallMapper.mapToHallDTO(shelf.getHall()));
        shelfDTO.setShelfCapacity(shelf.getShelfCapacity());
        shelfDTO.setOccupiedShelfCapacity(shelf.getOccupiedShelfCapacity());
        shelfDTO.setIsDeleted(shelf.getIsDeleted());
        return shelfDTO;
    }

    public static Shelf mapToShelfEntity(ShelfDTO shelfDTO) {
        Shelf shelf = new Shelf();
        shelf.setId(shelfDTO.getId());
        shelf.setShelfNumber(shelfDTO.getShelfNumber());
        shelf.setShelfCapacity(shelfDTO.getShelfCapacity());
        shelf.setOccupiedShelfCapacity(shelfDTO.getOccupiedShelfCapacity());
        shelf.setIsDeleted(shelfDTO.getIsDeleted());
        shelf.setHall(HallMapper.mapToHallEntity(shelfDTO.getHallDTO()));
        return shelf;
    }
}
