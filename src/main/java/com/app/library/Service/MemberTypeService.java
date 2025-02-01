package com.app.library.Service;

import com.app.library.DTO.MemberTypeDTO;

import java.util.List;

public interface MemberTypeService {

    MemberTypeDTO getMemberType(Long id);

    List<MemberTypeDTO> getAllMemberTypes();

    MemberTypeDTO addMemberType(MemberTypeDTO memberTypeDTO);

    MemberTypeDTO updateMemberType(MemberTypeDTO memberTypeDTO);

    int deleteMemberType(Long id);

}
