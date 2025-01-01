package com.app.library.DTO;

import lombok.Data;

@Data
public class RowDTO {

    private Long id;

    private Integer rowNumber;

    private Integer Capacity;

    private Integer occupiedCapacity;

    private ShelfDTO shelfDTO;

    private Boolean isDeleted = false;
}
