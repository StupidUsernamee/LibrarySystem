package com.app.library.Mapper;

import com.app.library.DTO.BorrowDTO;
import com.app.library.Entity.Borrow;

public class BorrowMapper {

    // map entity to dto
    public static BorrowDTO mapEntityToDTO(Borrow borrow) {
        BorrowDTO dto = new BorrowDTO();
        dto.setId(borrow.getId());
        dto.setBookDTO(BookMapper.mapToBookDTO(borrow.getBook()));
        dto.setMemberDTO(MemberMapper.mapToDTO(borrow.getMember()));
        dto.setBorrowDate(borrow.getBorrowDate());
        dto.setReturnDate(borrow.getReturnDate());
        dto.setPenalty(borrow.getPenalty());
        return dto;
    }

    // map dto to entity
    public static Borrow mapDtoToEntity(BorrowDTO dto) {
        Borrow borrow = new Borrow();
        borrow.setId(dto.getId());
        borrow.setBook(BookMapper.mapToBookEntity(dto.getBookDTO()));
        borrow.setMember(MemberMapper.mapToEntity(dto.getMemberDTO()));
        borrow.setBorrowDate(dto.getBorrowDate());
        borrow.setReturnDate(dto.getReturnDate());
        borrow.setPenalty(dto.getPenalty());
        return borrow;
    }
}
