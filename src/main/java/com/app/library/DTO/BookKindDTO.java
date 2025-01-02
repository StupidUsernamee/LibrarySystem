package com.app.library.DTO;

import lombok.Data;

@Data
public class BookKindDTO {

    private Long id;

    private String title;

    private Boolean isDeleted;
}
