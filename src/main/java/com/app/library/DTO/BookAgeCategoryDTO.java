package com.app.library.DTO;

import lombok.Data;

@Data
public class BookAgeCategoryDTO {

    private Long id;

    private String title;

    private Integer startAge;

    private Integer endAge;

    private Boolean isDeleted = false;
}
