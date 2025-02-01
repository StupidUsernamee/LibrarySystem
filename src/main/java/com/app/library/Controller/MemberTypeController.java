package com.app.library.Controller;


import com.app.library.DTO.MemberTypeDTO;
import com.app.library.Service.MemberTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("api/MType")
@AllArgsConstructor
public class MemberTypeController {

    private final MemberTypeService memberTypeService;

    @GetMapping("getType/{id}")
    public ResponseEntity<MemberTypeDTO> getMemberType(@PathVariable Long id) {
        MemberTypeDTO memberTypeDTO = memberTypeService.getMemberType(id);
        String message;
        HttpHeaders headers = new HttpHeaders();

        if (memberTypeDTO == null) {
            message = MessageFormat.format("memberType[{0}] not found.", id);
            headers.add("error-message", message);
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        headers.add(HttpHeaders.ACCEPT, "success.");
        return new ResponseEntity<>(memberTypeDTO, headers, HttpStatus.OK);
    }

    @GetMapping("getAllTypes")
    public ResponseEntity<List<MemberTypeDTO>> getAllMemberTypes() {
        List<MemberTypeDTO> memberTypeDTOList = memberTypeService.getAllMemberTypes();

        String message;
        HttpHeaders headers = new HttpHeaders();
        if (memberTypeDTOList.isEmpty()) {
            message = "There are no memberTypes in the database.";
            headers.add("error-message", message);
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        headers.add(HttpHeaders.ACCEPT, "success");
        return new ResponseEntity<>(memberTypeDTOList, headers, HttpStatus.OK);
    }

    @PostMapping("addMType")
    public ResponseEntity<MemberTypeDTO> addMemberType(@RequestBody MemberTypeDTO memberTypeDTO) {
        MemberTypeDTO newMemberTypeDTO = memberTypeService.addMemberType(memberTypeDTO);
        String message = "Member type added successfully.";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", message);
        return new ResponseEntity<>(newMemberTypeDTO, headers, HttpStatus.CREATED);
    }

    @PatchMapping("updateMType/{id}")
    public ResponseEntity<MemberTypeDTO> updateMemberType(@PathVariable Long id, @RequestBody MemberTypeDTO memberTypeDTO) {
        memberTypeDTO.setId(id);
        MemberTypeDTO updatedMemberTypeDTO = memberTypeService.updateMemberType(memberTypeDTO);
        String message;
        HttpHeaders headers = new HttpHeaders();
        if (updatedMemberTypeDTO == null) {
            message = MessageFormat.format("memberType[{0}] not found.", id);
            headers.add("error-message", message);
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        message = MessageFormat.format("memberType[{0}] updated successfully.", id);
        headers.add(HttpHeaders.ACCEPT, message);
        return new ResponseEntity<>(updatedMemberTypeDTO, headers, HttpStatus.OK);
    }

    @DeleteMapping("daleteMType/{id}")
    public ResponseEntity<MemberTypeDTO> deleteMemberType(@PathVariable Long id) {
        int result = memberTypeService.deleteMemberType(id);
        String message;
        HttpHeaders headers = new HttpHeaders();

        return switch (result) {
            case -1: {
                message = MessageFormat.format("memberType[{0}] not found.", id);
                headers.add("error-message", message);
                yield new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
            }
            case 0: {
                message = MessageFormat.format("memberType[{0}] already deleted.", id);
                headers.add("error-message", message);
                yield new ResponseEntity<>(headers, HttpStatus.CONFLICT);
            }
            case 1: {
                message = MessageFormat.format("memberType[{0}] deleted successfully.", id);
                headers.add(HttpHeaders.ACCEPT, message);
                yield new ResponseEntity<>(headers, HttpStatus.OK);
            }
            default: {
                message = "An unknown error occurred.";
                headers.add("error-message", message);
                yield new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
