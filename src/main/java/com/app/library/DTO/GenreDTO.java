package com.app.library.DTO;

import lombok.Data;

@Data
public class GenreDTO {

    private Long id;

    private String genreTitle;

    private Boolean isDeleted = false;
}
