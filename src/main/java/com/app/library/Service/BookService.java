package com.app.library.Service;

import com.app.library.DTO.BookDTO;

import java.util.List;

public interface BookService {

    BookDTO addBook(BookDTO dto);

    BookDTO getBookById(Long id);

    List<BookDTO> getBooks();

    List<BookDTO> getBookByCriteria(
            String title,
            String publisher,
            String language
    );

    BookDTO updateBook(BookDTO dto);

    int deleteBook(Long id);
}
