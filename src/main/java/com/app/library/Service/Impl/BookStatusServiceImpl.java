package com.app.library.Service.Impl;

import com.app.library.DTO.BookStatusDTO;
import com.app.library.Entity.BookStatus;
import com.app.library.Mapper.BookStatusMapper;
import com.app.library.Repository.BookStatusRepository;
import com.app.library.Service.BookStatusService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class BookStatusServiceImpl implements BookStatusService {

    private final BookStatusRepository bookStatusRepository;

    @Override
    public BookStatusDTO getBookStatus(Long id) {
        Optional<BookStatus> bookStatus = bookStatusRepository.findById(id);
        if (bookStatus.isEmpty()) {
            return null;
        } else if (bookStatus.get().getIsDeleted()) {
            return null;
        }
        return BookStatusMapper.mapToBookStatusDTO(bookStatus.get());
    }

    @Override
    public List<BookStatusDTO> getAllBookStatus() {
        List<BookStatus> bookStatus = bookStatusRepository.findAll();

        List<BookStatusDTO> bookStatusDTOList = new ArrayList<>(bookStatus.stream().map(BookStatusMapper::mapToBookStatusDTO).toList());

        bookStatusDTOList.removeIf(BookStatusDTO::getIsDeleted);

        return bookStatusDTOList;
    }

    @Override
    public BookStatusDTO addBookStatus() {
        BookStatus bookStatus = new BookStatus();
        return BookStatusMapper.mapToBookStatusDTO(bookStatusRepository.save(bookStatus));
    }

    @Override
    public BookStatusDTO markStatusRequested(Long id) {
        Optional<BookStatus> bookStatusOptional = bookStatusRepository.findById(id);

        if (bookStatusOptional.isEmpty()) {
            return null;
        } else if (bookStatusOptional.get().getIsDeleted()) {
            return null;
        }
        BookStatus bookStatus = bookStatusOptional.get();
        SetAllFalse(bookStatus);
        bookStatus.setRequested(true);
        BookStatus savedBookStatus = bookStatusRepository.save(bookStatus);
        return BookStatusMapper.mapToBookStatusDTO(savedBookStatus);

    }


    @Override
    public BookStatusDTO markStatusAvailable(Long id) {
        Optional<BookStatus> bookStatusOptional = bookStatusRepository.findById(id);

        if (bookStatusOptional.isEmpty()) {
            return null;
        } else if (bookStatusOptional.get().getIsDeleted()) {
            return null;
        }
        BookStatus bookStatus = bookStatusOptional.get();
        SetAllFalse(bookStatus);
        bookStatus.setAvailable(true);
        BookStatus savedBookStatus = bookStatusRepository.save(bookStatus);
        return BookStatusMapper.mapToBookStatusDTO(savedBookStatus);
    }

    @Override
    public BookStatusDTO markStatusMissing(Long id) {
        Optional<BookStatus> bookStatusOptional = bookStatusRepository.findById(id);

        if (bookStatusOptional.isEmpty()) {
            return null;
        } else if (bookStatusOptional.get().getIsDeleted()) {
            return null;
        }
        BookStatus bookStatus = bookStatusOptional.get();
        SetAllFalse(bookStatus);
        bookStatus.setMissing(true);
        BookStatus savedBookStatus = bookStatusRepository.save(bookStatus);
        return BookStatusMapper.mapToBookStatusDTO(savedBookStatus);
    }

    @Override
    public BookStatusDTO markStatusReserved(Long id) {
        Optional<BookStatus> bookStatusOptional = bookStatusRepository.findById(id);
        if (bookStatusOptional.isEmpty()) {
            return null;
        } else if (bookStatusOptional.get().getIsDeleted()) {
            return null;
        }
        BookStatus bookStatus = bookStatusOptional.get();
        SetAllFalse(bookStatus);
        bookStatus.setReserved(true);
        BookStatus savedBookStatus = bookStatusRepository.save(bookStatus);
        return BookStatusMapper.mapToBookStatusDTO(savedBookStatus);
    }

    @Override
    public BookStatusDTO markStatusBorrowed(Long id) {
        Optional<BookStatus> bookStatusOptional = bookStatusRepository.findById(id);
        if (bookStatusOptional.isEmpty()) {
            return null;
        } else if (bookStatusOptional.get().getIsDeleted()) {
            return null;
        }
        BookStatus bookStatus = bookStatusOptional.get();
        SetAllFalse(bookStatus);
        bookStatus.setBorrowed(true);
        BookStatus savedBookStatus = bookStatusRepository.save(bookStatus);
        return BookStatusMapper.mapToBookStatusDTO(savedBookStatus);
    }

    private void SetAllFalse(BookStatus bookStatus) {
        bookStatus.setIsDeleted(false);
        bookStatus.setAvailable(false);
        bookStatus.setRequested(false);
        bookStatus.setMissing(false);
        bookStatus.setReserved(false);
        bookStatus.setBorrowed(false);
    }

    @Override
    public int deleteBookStatus(Long id) {
        Optional<BookStatus> bookStatusOptional = bookStatusRepository.findById(id);
        if (bookStatusOptional.isEmpty()) {
            return -1;
        } else if (bookStatusOptional.get().getIsDeleted()) {
            return 0;
        }
        BookStatus bookStatus = bookStatusOptional.get();
        bookStatus.setIsDeleted(false);
        bookStatusRepository.save(bookStatus);
        return 1;
    }
}
