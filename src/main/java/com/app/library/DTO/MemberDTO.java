package com.app.library.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String nationalId;

    private String email;

    private String mobilePhone;

    private Integer age;

    private String education;

    private Boolean gender;

    private MemberTypeDTO memberTypeDTO;

    private LocalDate joinDate;

    private Boolean isDeleted = false;
}
