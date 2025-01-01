package com.app.library.DTO;

import lombok.Data;

@Data
public class ShelfDTO {

    private Long id;

    private Integer ShelfNumber;

    private Integer ShelfCapacity;

    private Integer OccupiedShelfCapacity = 0;

    private HallDTO hallDTO;

    private Boolean isDeleted;
}
