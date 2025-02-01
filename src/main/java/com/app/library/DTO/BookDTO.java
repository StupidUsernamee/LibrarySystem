package com.app.library.DTO;

import com.app.library.Entity.Book;
import lombok.Data;

@Data
public class BookDTO {

    private Long id;

    private String title;

    private Integer pageCount;

    private String publisher;

    private AuthorDTO authorDTO;

    private Integer publishYear;

    private String language;

    private GenreDTO genreDTO;

    private BookKindDTO bookKindDTO;

    private BookAgeCategoryDTO bookAgeCategoryDTO;

    private RowDTO rowDTO;

    private Boolean isDeleted = false;

    private Integer bookNumber;

    private String bookCode;

    private BookStatusDTO bookStatusDTO;
}
