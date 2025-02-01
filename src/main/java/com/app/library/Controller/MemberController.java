package com.app.library.Controller;

import com.app.library.DTO.MemberDTO;
import com.app.library.Service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("addMember")
    public ResponseEntity<MemberDTO> addMember(@RequestBody MemberDTO dto) {
        MemberDTO memberDTO = memberService.addMember(dto);
        if (memberDTO == null) {
            String message = "Cannot create a new member!";
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(memberDTO, HttpStatus.CREATED);
    }

    @GetMapping("getMember/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        MemberDTO memberDTO = memberService.getMemberById(id);
        if (memberDTO == null) {
            String message = MessageFormat.format("Member[{0}] not  found!", id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @GetMapping("getMembers")
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> memberDTOList = memberService.getAllMembers();

        return new ResponseEntity<>(memberDTOList, HttpStatus.OK);
    }

    @GetMapping("searchMembers")
    public ResponseEntity<List<MemberDTO>> searchByCriteria(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nationalId
    ) {
        if (firstName == null && lastName == null && email == null && nationalId == null) {
            String message = "At least one of the criteria must be provided!";
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }
        List<MemberDTO> memberDTOList = memberService.getMembersByCriteria(firstName, lastName, email, nationalId);

        return new ResponseEntity<>(memberDTOList, HttpStatus.OK);
    }

    @PatchMapping("updateMember/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable("id") Long id, @RequestBody MemberDTO dto) {
        dto.setId(id);
        MemberDTO updatedMemberDTO = memberService.updateMember(dto);
        if (updatedMemberDTO == null) {
            String message = MessageFormat.format("Member[{0}] cannot be found!", id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("error-message", message);
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedMemberDTO, HttpStatus.OK);
    }

    @DeleteMapping("deleteMember/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable("id") Long id) {
        int result = memberService.deleteMember(id);
        String message;
        return switch (result) {
             case -1: {
                message = MessageFormat.format("member[{0}] cannot be found.", id);
                yield new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            case 0: {
                message = MessageFormat.format("member[{0}] already deleted.", id);
                yield new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            case 1: {
                message = MessageFormat.format("member[{0}] deleted successfully.", id);
                yield new ResponseEntity<>(message, HttpStatus.OK);
            }
            default: {
                message = "An unexpected error occurred.";
                yield new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
