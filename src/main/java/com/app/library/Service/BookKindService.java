package com.app.library.Service;

import com.app.library.DTO.BookKindDTO;

import java.util.List;

public interface BookKindService {

    BookKindDTO addBookKind(BookKindDTO bookKindDTO);

    BookKindDTO getBookKindById(Long id);

    List<BookKindDTO> getAllBookKind();

    BookKindDTO getBookKindByTitle(String title);

    BookKindDTO updateBookKind(BookKindDTO bookKindDTO);

    int deleteBookKind(Long id);
}
