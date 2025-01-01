package com.app.library.Service;

import com.app.library.DTO.LibraryDTO;

import java.util.List;

public interface LibraryService {

    LibraryDTO createLibrary(LibraryDTO libraryDTO);

    List<LibraryDTO> getLibraries();

    LibraryDTO getLibraryByNumber(Long id);

    Integer getLastLibraryNumber();

    LibraryDTO updateLibrary(LibraryDTO libraryDTO);

    int deleteLibrary(Long libraryNumber); // In this system deletion happens in soft-delete way

    List<LibraryDTO> getLibraryByCriteria(String libraryName, String address, Long libraryNumber);
}
