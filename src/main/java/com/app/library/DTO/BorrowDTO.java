package com.app.library.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowDTO {

    private Long id;

    private BookDTO bookDTO;

    private MemberDTO memberDTO;

    private LocalDate borrowDate;

    private LocalDate returnDate;

    private Integer penalty = 0;
}
