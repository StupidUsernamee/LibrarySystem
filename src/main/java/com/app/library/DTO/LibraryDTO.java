package com.app.library.DTO;

import lombok.Data;

@Data
public class LibraryDTO {

    // ID not included in DTO since ID must not be shown to the client. TODO: find out if should remain or not
    private Long id;

    private String name;

    private Long libraryNumber;

    private String address;

    private Boolean isDeleted;
}
