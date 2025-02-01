package com.app.library.Service.Impl;

import com.app.library.DTO.MemberDTO;
import com.app.library.Entity.Member;
import com.app.library.Mapper.MemberMapper;
import com.app.library.Repository.MemberRepository;
import com.app.library.Service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final EntityManager entityManager;

    @Override
    public MemberDTO addMember(MemberDTO dto) {
        dto.setIsDeleted(false);
        dto.setJoinDate(LocalDate.now());
        try {
            Member savedMember = memberRepository.save(MemberMapper.mapToEntity(dto));
            return MemberMapper.mapToDTO(savedMember);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public MemberDTO getMemberById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isEmpty()) {
            return null;
        }
        if (memberOptional.get().getIsDeleted()) {
            return null;
        }

        return MemberMapper.mapToDTO(memberOptional.get());
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        List<Member> memberList = memberRepository.findAll();

        List<MemberDTO> memberDTOList = new ArrayList<>(memberList
                .stream().
                map(MemberMapper::mapToDTO)
                .toList());

        memberDTOList.removeIf(MemberDTO::getIsDeleted);

        return memberDTOList;
    }

    @Override
    public List<MemberDTO> getMembersByCriteria(String firstName, String lastName, String email, String nationalId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Member.class);
        Root<Member> root = cq.from(Member.class);

        List<Predicate> predicates = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.like(root.get("firstName"), "%" + firstName + "%"));
        }
        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.like(root.get("lastName"), "%" + lastName + "%"));
        }
        if (email != null && !email.isEmpty()) {
            predicates.add(cb.equal(root.get("email"), email));
        }
        if (nationalId != null && !nationalId.isEmpty()) {
            predicates.add(cb.equal(root.get("nationalId"), nationalId));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        List<Member> memberList = entityManager.createQuery(cq).getResultList();

        memberList.removeIf(Member::getIsDeleted);

        return memberList
                .stream()
                .map(MemberMapper::mapToDTO)
                .toList();
    }

    @Override
    public MemberDTO updateMember(MemberDTO dto) {
        Optional<Member> memberOptional = memberRepository.findById(dto.getId());
        if (memberOptional.isEmpty()) {
            return null;
        }
        if (memberOptional.get().getIsDeleted()) {
            return null;
        }
        Member memberToUpdate = memberOptional.get();
        updateEntityFromDTO(memberToUpdate, dto);
        Member updatedMember = memberRepository.save(memberToUpdate);
        return MemberMapper.mapToDTO(updatedMember);
    }

    private void updateEntityFromDTO(Member member, MemberDTO dto) {
        if (dto.getFirstName() != null) {
            member.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            member.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            member.setEmail(dto.getEmail());
        }
        // others will be implemented.
    }

    @Override
    public int deleteMember(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isEmpty()) {
            return -1;
        }
        if (memberOptional.get().getIsDeleted()) {
            return 0;
        }
        Member memberToDelete = memberOptional.get();
        memberToDelete.setIsDeleted(true);
        Member DeletedMember = memberRepository.save(memberToDelete);
        return 1;
    }
}
