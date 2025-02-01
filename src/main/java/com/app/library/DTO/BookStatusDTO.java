package com.app.library.DTO;

import lombok.Data;

@Data
public class BookStatusDTO {

    private Long id;

    private Boolean available = true;

    private Boolean missing = false;

    private Boolean borrowed = false;

    private Boolean requested = false;

    private Boolean reserved = false;

    private Boolean isDeleted = false;
}
