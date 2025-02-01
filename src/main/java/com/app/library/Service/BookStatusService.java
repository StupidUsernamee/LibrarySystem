package com.app.library.Service;

import com.app.library.DTO.BookStatusDTO;

import java.util.List;

public interface BookStatusService {

    BookStatusDTO getBookStatus(Long id);

    List<BookStatusDTO> getAllBookStatus();

    BookStatusDTO addBookStatus();

    BookStatusDTO markStatusAvailable(Long id);

    BookStatusDTO markStatusMissing(Long id);

    BookStatusDTO markStatusReserved(Long id);

    BookStatusDTO markStatusBorrowed(Long id);

    BookStatusDTO markStatusRequested(Long id);

    int deleteBookStatus(Long id);


}
