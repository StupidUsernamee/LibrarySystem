package com.app.library.Mapper;

import com.app.library.DTO.MemberTypeDTO;
import com.app.library.Entity.MemberType;
import lombok.Data;

@Data
public class MemberTypeMapper {

    public static MemberType mapToMemberTypeEntity(MemberTypeDTO memberTypeDTO) {
        MemberType memberType = new MemberType();
        memberType.setId(memberTypeDTO.getId());
        memberType.setTitle(memberTypeDTO.getTitle());
        memberType.setPenaltyRate(memberTypeDTO.getPenaltyRate());
        memberType.setMaxBorrowTime(memberTypeDTO.getMaxBorrowTime());
        memberType.setMaxBorrowCount(memberTypeDTO.getMaxBorrowCount());
        memberType.setIsDeleted(memberTypeDTO.getIsDeleted());
        return memberType;
    }

    public static MemberTypeDTO mapToMemberTypeDTO(MemberType memberType) {
        MemberTypeDTO memberTypeDTO = new MemberTypeDTO();
        memberTypeDTO.setId(memberType.getId());
        memberTypeDTO.setTitle(memberType.getTitle());
        memberTypeDTO.setPenaltyRate(memberType.getPenaltyRate());
        memberTypeDTO.setMaxBorrowTime(memberType.getMaxBorrowTime());
        memberTypeDTO.setMaxBorrowCount(memberType.getMaxBorrowCount());
        memberTypeDTO.setIsDeleted(memberType.getIsDeleted());
        return memberTypeDTO;
    }
}
