package com.app.library.DTO;

import lombok.Data;

@Data
public class HallDTO {

    private Long id;

    private Long HallNumber;

    private Integer capacity;

    private LibraryDTO libraryDTO;

    private Boolean isDeleted;
}
