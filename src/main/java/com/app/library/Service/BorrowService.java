package com.app.library.Service;

import com.app.library.DTO.BorrowDTO;

import java.util.List;
import java.util.Map;

public interface BorrowService {

    public BorrowDTO addBorrow(BorrowDTO dto);

    public BorrowDTO getBorrowById(Long id);

    public List<BorrowDTO> getAllBorrows();

    public String returnBorrowedBook(Long id);

    public void increasePenalties();
}
