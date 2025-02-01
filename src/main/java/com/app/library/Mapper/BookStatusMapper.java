package com.app.library.Mapper;

import com.app.library.DTO.BookStatusDTO;
import com.app.library.Entity.BookStatus;
import lombok.Data;

@Data
public class BookStatusMapper {

    public static BookStatusDTO mapToBookStatusDTO(BookStatus bookStatus) {
        BookStatusDTO dto = new BookStatusDTO();
        dto.setId(bookStatus.getId());
        dto.setAvailable(bookStatus.getAvailable());
        dto.setMissing(bookStatus.getMissing());
        dto.setReserved(bookStatus.getReserved());
        dto.setRequested(bookStatus.getRequested());
        dto.setIsDeleted(bookStatus.getIsDeleted());
        return dto;
    }

    public static BookStatus mapToBookStatusEntity(BookStatusDTO bookStatusDTO) {
        BookStatus entity = new BookStatus();
        entity.setId(bookStatusDTO.getId());
        entity.setAvailable(bookStatusDTO.getAvailable());
        entity.setMissing(bookStatusDTO.getMissing());
        entity.setReserved(bookStatusDTO.getReserved());
        entity.setRequested(bookStatusDTO.getRequested());
        entity.setIsDeleted(bookStatusDTO.getIsDeleted());
        return entity;

    }
}
