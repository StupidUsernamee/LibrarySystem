package com.app.library.Service.Impl;


import com.app.library.DTO.MemberTypeDTO;
import com.app.library.Entity.MemberType;
import com.app.library.Mapper.MemberTypeMapper;
import com.app.library.Repository.MemberTypeRepository;
import com.app.library.Service.MemberTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberTypeServiceImpl implements MemberTypeService {

    private final MemberTypeRepository memberTypeRepository;

    @Override
    public MemberTypeDTO getMemberType(Long id) {
        Optional<MemberType> memberType = memberTypeRepository.findById(id);
        if (memberType.isEmpty()) {
            return null;
        }
        if (memberType.get().getIsDeleted()) {
            return null;
        }
        return MemberTypeMapper.mapToMemberTypeDTO(memberType.get());
    }

    @Override
    public List<MemberTypeDTO> getAllMemberTypes() {
        List<MemberType> memberTypes = memberTypeRepository.findAll();

        List<MemberTypeDTO> memberTypeDTOS = new ArrayList<>(memberTypes
                .stream()
                .map(MemberTypeMapper::mapToMemberTypeDTO)
                .toList());

        memberTypeDTOS.removeIf(MemberTypeDTO::getIsDeleted);

        return memberTypeDTOS;
    }

    @Override
    public MemberTypeDTO addMemberType(MemberTypeDTO memberTypeDTO) {
        MemberType memberType = MemberTypeMapper.mapToMemberTypeEntity(memberTypeDTO);

        MemberType savedMemberType = memberTypeRepository.save(memberType);

        return MemberTypeMapper.mapToMemberTypeDTO(savedMemberType);
    }

    @Override
    public MemberTypeDTO updateMemberType(MemberTypeDTO memberTypeDTO) {
        Optional<MemberType> memberTypeOptional = memberTypeRepository.findById(memberTypeDTO.getId());

        if (memberTypeOptional.isEmpty()) {
            return null;
        }
        if (memberTypeOptional.get().getIsDeleted()) {
            return null;
        }
        MemberType memberTypeToUpdate = memberTypeOptional.get();
        updateEntityFromDTO(memberTypeDTO, memberTypeToUpdate);
        MemberType savedMemberType = memberTypeRepository.save(memberTypeToUpdate);
        return MemberTypeMapper.mapToMemberTypeDTO(savedMemberType);

    }

    private void updateEntityFromDTO(MemberTypeDTO memberTypeDTO, MemberType memberType) {
        if (memberTypeDTO.getTitle() != null) {
            memberType.setTitle(memberTypeDTO.getTitle());
        }
        if (memberTypeDTO.getPenaltyRate() != null) {
            memberType.setPenaltyRate(memberTypeDTO.getPenaltyRate());
        }
        if (memberTypeDTO.getMaxBorrowTime() != null) {
            memberType.setMaxBorrowTime(memberTypeDTO.getMaxBorrowTime());
        }
        if (memberTypeDTO.getMaxBorrowCount() != null) {
            memberType.setMaxBorrowCount(memberTypeDTO.getMaxBorrowCount());
        }
    }

    @Override
    public int deleteMemberType(Long id) {
        Optional<MemberType> memberType = memberTypeRepository.findById(id);
        if (memberType.isEmpty()) {
            return -1;
        }
        if (memberType.get().getIsDeleted()) {
            return -1;
        }
        MemberType memberTypeToDelete = memberType.get();
        memberTypeToDelete.setIsDeleted(true);
        memberTypeRepository.save(memberTypeToDelete);
        return 1;
    }
}
