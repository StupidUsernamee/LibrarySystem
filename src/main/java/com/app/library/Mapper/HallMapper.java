package com.app.library.Mapper;


import com.app.library.DTO.HallDTO;
import com.app.library.Entity.Hall;
import lombok.Data;

@Data
public class HallMapper {

    // Map Hall entity to HallDTO
    public static HallDTO mapToHallDTO(Hall hall) {
        HallDTO dto = new HallDTO();
        dto.setId(hall.getId());
        dto.setHallNumber(hall.getHallNumber());
        dto.setLibraryDTO(LibraryMapper.mapToLibraryDTO(hall.getLibrary()));
        dto.setIsDeleted(hall.getIsDeleted());
        dto.setCapacity(hall.getCapacity());
        return dto;
    }

    public static Hall mapToHallEntity(HallDTO dto) {
        Hall hall = new Hall();
        hall.setId(dto.getId());
        hall.setHallNumber(dto.getHallNumber());
        if (dto.getLibraryDTO() != null)
            hall.setLibrary(LibraryMapper.mapToLibraryEntity(dto.getLibraryDTO()));
        hall.setIsDeleted(dto.getIsDeleted());
        hall.setCapacity(dto.getCapacity());
        return hall;
    }
}