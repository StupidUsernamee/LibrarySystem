package com.app.library.Mapper;

import com.app.library.DTO.MemberDTO;
import com.app.library.Entity.Member;
import lombok.Data;

@Data
public class MemberMapper {

    // map Entity to DTO
    public static Member mapToEntity(MemberDTO dto) {
        Member member = new Member();
        member.setId(dto.getId());
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setNationalId(dto.getNationalId());
        member.setEmail(dto.getEmail());
        member.setMobilePhone(dto.getMobilePhone());
        member.setAge(dto.getAge());
        member.setEducation(dto.getEducation());
        member.setGender(dto.getGender());
        member.setMember_type(MemberTypeMapper.mapToMemberTypeEntity(dto.getMemberTypeDTO()));
        member.setJoinDate(dto.getJoinDate());
        member.setIsDeleted(dto.getIsDeleted());
        return member;
    }


    // map DTO to entity
    public static MemberDTO mapToDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setNationalId(member.getNationalId());
        dto.setEmail(member.getEmail());
        dto.setMobilePhone(member.getMobilePhone());
        dto.setAge(member.getAge());
        dto.setEducation(member.getEducation());
        dto.setGender(member.getGender());
        dto.setMemberTypeDTO(MemberTypeMapper.mapToMemberTypeDTO(member.getMember_type()));
        dto.setJoinDate(member.getJoinDate());
        dto.setIsDeleted(member.getIsDeleted());
        return dto;
    }
}
