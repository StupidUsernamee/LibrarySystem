package com.app.library.DTO;

import lombok.Data;

@Data
public class AuthorDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String nationality;

    private String gender;

    private String email;

    private String phone;

    private Boolean isTranslator = false;

    private Boolean isDeleted = false;

    private String Address;
}
