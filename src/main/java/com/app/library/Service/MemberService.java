package com.app.library.Service;

import com.app.library.DTO.MemberDTO;

import java.util.List;

public interface MemberService {

    public MemberDTO addMember(MemberDTO dto);

    public MemberDTO getMemberById(Long id);

    public List<MemberDTO> getAllMembers();

    public List<MemberDTO> getMembersByCriteria(
            String firstName,
            String lastName,
            String email,
            String nationalId
    );

    public MemberDTO updateMember(MemberDTO dto);

    public int deleteMember(Long id);
}
