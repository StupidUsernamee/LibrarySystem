package com.app.library.Mapper;

import com.app.library.DTO.BookDTO;
import com.app.library.Entity.Book;
import lombok.Data;

@Data
public class BookMapper {

    // map entity to DTO
    public static BookDTO mapToBookDTO(Book book) {

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPageCount(book.getPageCount());
        dto.setPublisher(book.getPublisher());
        dto.setPublishYear(book.getPublishYear());
        dto.setLanguage(book.getLanguage());
        dto.setGenreDTO(GenreMapper
                .mapToGenreDTO(book.getGenre()));
        dto.setBookStatusDTO(BookStatusMapper
                .mapToBookStatusDTO(book.getBookStatus()));
        dto.setBookKindDTO(BookKindMapper
                .mapToBookKindDTO(book.getKindOfBook()));
        dto.setBookAgeCategoryDTO(BookAgeCategoryMapper
                .mapToBookAgeCategoryDTO(book.getAgeCategory()));
        dto.setRowDTO(RowMapper
                .mapToRowDTO(book.getRow()));
        dto.setIsDeleted(book.getIsDeleted());
        dto.setBookNumber(book.getBookNumber());
        dto.setAuthorDTO(AuthorMapper
                .mapToAuthorDTO(book.getAuthor()));

        return dto;
    }

    //map to entity
    public static Book mapToBookEntity(BookDTO dto) {

        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setPageCount(dto.getPageCount());
        book.setPublisher(dto.getPublisher());
        book.setPublishYear(dto.getPublishYear());
        book.setLanguage(dto.getLanguage());
        book.setGenre(GenreMapper
                .mapToGenreEntity(dto.getGenreDTO()));
        book.setBookStatus(BookStatusMapper
                .mapToBookStatusEntity(dto.getBookStatusDTO()));
        book.setKindOfBook(BookKindMapper
                .mapToBookKindEntity(dto.getBookKindDTO()));
        book.setAgeCategory(BookAgeCategoryMapper
                .mapToAgeCategoryEntity(dto.getBookAgeCategoryDTO()));
        book.setRow(RowMapper
                .mapToRowEntity(dto.getRowDTO()));
        book.setIsDeleted(dto.getIsDeleted());
        book.setBookNumber(dto.getBookNumber());
        book.setAuthor(AuthorMapper
                .mapToAuthorEntity(dto.getAuthorDTO()));

        return book;
    }
}
