package com.app.library.Service.Impl;

import com.app.library.DTO.BookDTO;
import com.app.library.Entity.Book;
import com.app.library.Mapper.BookMapper;
import com.app.library.Repository.BookRepository;
import com.app.library.Service.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;

    @Override
    public BookDTO addBook(BookDTO dto) {
        int rowNumber = dto.getRowDTO().getRowNumber();
        int shelfNumber = dto.getRowDTO().getShelfDTO().getShelfNumber();
        Long hallNumber = dto.getRowDTO().getShelfDTO().getHallDTO().getHallNumber();
        Long libraryNumber = dto.getRowDTO().getShelfDTO().getHallDTO().getLibraryDTO().getLibraryNumber();
        String bookCode = String.valueOf(libraryNumber)
                + "-" + String.valueOf(hallNumber)
                + "-" + String.valueOf(shelfNumber)
                + "-" + String.valueOf(rowNumber);

        dto.setBookCode(bookCode);
        dto.setIsDeleted(false);
        Book savedBook = bookRepository.save(BookMapper.mapToBookEntity(dto));
        return BookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public BookDTO getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty())
            return null;
        if (bookOptional.get().getIsDeleted())
            return null;

        return BookMapper.mapToBookDTO(bookOptional.get());
    }

    @Override
    public List<BookDTO> getBooks() {
        List<Book> bookList = bookRepository.findAll();

        List<BookDTO> bookDTOList = new ArrayList<>(bookList.stream().map(BookMapper::mapToBookDTO).toList());

        bookDTOList.removeIf(BookDTO::getIsDeleted);
        return bookDTOList;
    }

    @Override
    public List<BookDTO> getBookByCriteria(String title, String publisher, String language) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            predicates.add(cb.like(root.get("title"), "%" + title + "%"));
        }

        if (publisher != null && !publisher.isEmpty()) {
            predicates.add(cb.like(root.get("publisher"), "%" + publisher + "%"));
        }

        if (language != null && !language.isEmpty()) {
            predicates.add(cb.like(root.get("language"), "%" + language + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        List<Book> books = entityManager.createQuery(cq).getResultList();

        books.removeIf(Book::getIsDeleted);

        return books.stream()
                .map(BookMapper::mapToBookDTO)
                .toList();
    }

    @Override
    public BookDTO updateBook(BookDTO dto) {
        Optional<Book> bookOptional = bookRepository.findById(dto.getId());
        if (bookOptional.isEmpty())
            return null;
        if (bookOptional.get().getIsDeleted())
            return null;

        Book bookToUpdate = bookOptional.get();
        updateEntityFromDTO(bookToUpdate, dto);
        Book updatedBook = bookRepository.save(bookToUpdate);
        return BookMapper.mapToBookDTO(updatedBook);
    }

    private void updateEntityFromDTO(Book book, BookDTO bookDTO) {
        if (bookDTO.getPageCount() != null) {
            book.setPageCount(bookDTO.getPageCount());
        }
        if (bookDTO.getTitle() != null) {
            book.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getPublisher() != null) {
            book.setPublisher(bookDTO.getPublisher());
        }
        // Other will be implemented.
    }

    @Override
    public int deleteBook(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isEmpty())
            return -1;

        if (bookOptional.get().getIsDeleted())
            return 0;

        Book bookToDelete = bookOptional.get();
        bookToDelete.setIsDeleted(true);
        Book deletedBook = bookRepository.save(bookToDelete);

        return 1;
    }
}
