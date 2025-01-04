package com.app.library.Service.Impl;

import com.app.library.DTO.BookKindDTO;
import com.app.library.Entity.BookKind;
import com.app.library.Mapper.BookKindMapper;
import com.app.library.Repository.BookKindRepository;
import com.app.library.Service.BookKindService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookKindServiceImpl implements BookKindService {

    private BookKindRepository bookKindRepository;

    @Override
    public BookKindDTO addBookKind(String bookKindTitle) {
        BookKind bookKind = new BookKind();
        bookKind.setTitle(bookKindTitle);
        bookKindRepository.save(bookKind);
        return BookKindMapper.mapToBookKindDTO(bookKind);
    }

    @Override
    public BookKindDTO getBookKindById(Long id) {
        Optional<BookKind> bookKind = bookKindRepository.findById(id);
        return bookKind.map(BookKindMapper::mapToBookKindDTO).orElse(null);

    }

    @Override
    public List<BookKindDTO> getAllBookKind() {
        List<BookKind> bookKindList = bookKindRepository.findAll();
        List<BookKindDTO> bookKindDTOList = new ArrayList<>(bookKindList.stream().map(BookKindMapper::mapToBookKindDTO).toList());

        bookKindDTOList.removeIf(BookKindDTO::getIsDeleted);

        return bookKindDTOList;
    }

    @Override
    public BookKindDTO getBookKindByTitle(String title) {
        BookKind bookKind = bookKindRepository.findByTitleContainingIgnoreCase(title);
        if(bookKind.getIsDeleted()) {
            return null;
        }
        return BookKindMapper.mapToBookKindDTO(bookKind);
    }

    @Override
    public BookKindDTO updateBookKind(BookKindDTO bookKindDTO) {
        Optional<BookKind> optionalBookKind = bookKindRepository.findById(bookKindDTO.getId());
        if(optionalBookKind.isEmpty()) {
            return null;
        }
        BookKind bookKindToUpdate = optionalBookKind.get();
        if(bookKindToUpdate.getIsDeleted()) {
            return null;
        }
        updateEntityFromDTO(bookKindDTO, bookKindToUpdate);
        return BookKindMapper.mapToBookKindDTO(bookKindRepository.save(bookKindToUpdate));
    }

    private void updateEntityFromDTO(BookKindDTO bookKindDTO, BookKind bookKind) {
        if(bookKindDTO.getTitle() != null) {
            bookKind.setTitle(bookKindDTO.getTitle());
        }
    }

    @Override
    public int deleteBookKind(Long id) {
        Optional<BookKind> bookKindOptional = bookKindRepository.findById(id);
        if(bookKindOptional.isEmpty()) {
            return -1;
        }
        BookKind bookKind = bookKindOptional.get();
        if(bookKind.getIsDeleted()) {
            return 0;
        }
        bookKind.setIsDeleted(true);
        bookKindRepository.save(bookKind);
        return 1;

    }
}
