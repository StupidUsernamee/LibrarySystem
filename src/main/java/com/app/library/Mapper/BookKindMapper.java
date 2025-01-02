package com.app.library.Mapper;

import com.app.library.DTO.BookKindDTO;
import com.app.library.Entity.BookKind;

public class BookKindMapper {

    public static BookKind mapToBookKindEntity(BookKindDTO bookKindDTO) {
        BookKind bookKind = new BookKind();
        bookKind.setId(bookKindDTO.getId());
        bookKind.setTitle(bookKindDTO.getTitle());
        return bookKind;
    }

    public static BookKindDTO mapToBookKindDTO(BookKind bookKind) {
        BookKindDTO bookKindDTO = new BookKindDTO();
        bookKindDTO.setId(bookKind.getId());
        bookKindDTO.setTitle(bookKind.getTitle());
        return bookKindDTO;
    }
}
