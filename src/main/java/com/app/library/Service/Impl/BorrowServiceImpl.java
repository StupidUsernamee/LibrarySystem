package com.app.library.Service.Impl;

import com.app.library.DTO.BookStatusDTO;
import com.app.library.DTO.BorrowDTO;
import com.app.library.Entity.BookStatus;
import com.app.library.Entity.Borrow;
import com.app.library.Mapper.BookStatusMapper;
import com.app.library.Mapper.BorrowMapper;
import com.app.library.Repository.BorrowRepository;
import com.app.library.Service.BookStatusService;
import com.app.library.Service.BorrowService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookStatusService bookStatusService;
    private final Logger logger = LoggerFactory.getLogger(BorrowServiceImpl.class);

    @Override
    public BorrowDTO addBorrow(BorrowDTO dto) {
        int borrowLimit = dto.getMemberDTO().getMemberTypeDTO().getMaxBorrowCount();
        int borrowedBooks = 0;
        Long memberId = dto.getMemberDTO().getId();
        List<Borrow> borrowList = borrowRepository.findAll();
        for (Borrow borrow : borrowList) {
            if (Objects.equals(borrow.getMember().getId(), memberId)) {
                borrowedBooks++;
                if (borrowedBooks >= borrowLimit) {
                    return null;
                }
            }
        }
        LocalDate now = LocalDate.now();
        dto.setBorrowDate(now);
        BookStatusDTO bookStatusDTO = dto.getBookDTO().getBookStatusDTO();
        bookStatusDTO = bookStatusService.markStatusBorrowed(bookStatusDTO.getId());
        dto.getBookDTO().setBookStatusDTO(bookStatusDTO);

        Borrow savedBorrow = borrowRepository.save(BorrowMapper.mapDtoToEntity(dto));

        return BorrowMapper.mapEntityToDTO(savedBorrow);


    }

    @Override
    public BorrowDTO getBorrowById(Long id) {
        Optional<Borrow> borrowOptional = borrowRepository.findById(id);
        return borrowOptional.map(BorrowMapper::mapEntityToDTO).orElse(null);
    }

    @Override
    public List<BorrowDTO> getAllBorrows() {
        List<Borrow> borrowList = borrowRepository.findAll();

        return new ArrayList<>(borrowList
                .stream()
                .map(BorrowMapper::mapEntityToDTO)
                .toList());
    }

    @Override
    public String returnBorrowedBook(Long id) {
        Optional<Borrow> borrowOptional = borrowRepository.findById(id);
        Map<String, String> result = new HashMap<>();
        if (borrowOptional.isEmpty()) {
            return "failed1"; // not exist
        }
        Borrow borrow = borrowOptional.get();
        if (borrow.getReturnDate() != null) {
            return "failed2"; // already returned
        }
        LocalDate now = LocalDate.now();
        borrow.setReturnDate(now);
        BookStatus bookStatus = borrow.getBook().getBookStatus();
        BookStatusDTO newBookStatusDTO = bookStatusService.markStatusAvailable(BookStatusMapper.mapToBookStatusDTO(bookStatus).getId());
        borrow.getBook().setBookStatus(BookStatusMapper.mapToBookStatusEntity(newBookStatusDTO));

        borrowRepository.save(borrow);
        return "success";
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void increasePenalties() {
        logger.info("IncreasePenalties initialized");
        ArrayList<Borrow> borrowArrayList = (ArrayList<Borrow>) borrowRepository.findAll();
        borrowArrayList.removeIf(Borrow -> Borrow.getReturnDate() != null);
        for (Borrow borrow : borrowArrayList) {
            int penaltyRate = borrow.getMember().getMember_type().getPenaltyRate();
            int maxHoldDays = borrow.getMember().getMember_type().getMaxBorrowTime();
            LocalDate now = LocalDate.now();
            LocalDate borrowedDate = borrow.getBorrowDate();
            long daysDifference = ChronoUnit.DAYS.between(borrowedDate, now);
            long daysPast = daysDifference - maxHoldDays;
            if (daysPast > 0) {
                logger.info("Penalty added to Borrow[{}].", borrow.getId());
                int penalty = (int)daysPast * penaltyRate;
                borrow.setPenalty(penalty);
                borrowRepository.save(borrow);
            }
        }
        logger.info("IncreasePenalties method executed.");
    }
}
