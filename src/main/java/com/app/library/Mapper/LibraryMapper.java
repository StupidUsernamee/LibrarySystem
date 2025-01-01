package com.app.library.Mapper;

import com.app.library.DTO.LibraryDTO;
import com.app.library.Entity.Library;
import lombok.Data;

@Data
public class LibraryMapper {

    public static LibraryDTO mapToLibraryDTO(Library library) {
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setId(library.getId());
        libraryDTO.setName(library.getName());
        libraryDTO.setLibraryNumber(library.getLibraryNumber());
        libraryDTO.setAddress(library.getAddress());
        libraryDTO.setIsDeleted(library.getIsDeleted());
        return libraryDTO;
    }

    public static Library mapToLibraryEntity(LibraryDTO libraryDTO) {
        Library library = new Library();
        library.setId(libraryDTO.getId());
        library.setName(libraryDTO.getName());
        library.setLibraryNumber(libraryDTO.getLibraryNumber());
        library.setAddress(libraryDTO.getAddress());
        library.setIsDeleted(libraryDTO.getIsDeleted());
        return library;
    }
}
