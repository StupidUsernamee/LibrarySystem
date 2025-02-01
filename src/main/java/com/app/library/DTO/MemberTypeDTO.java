package com.app.library.DTO;

import lombok.Data;

@Data
public class MemberTypeDTO {

    private Long id;

    private String title;

    private Integer penaltyRate;

    private Integer maxBorrowCount;

    private Integer maxBorrowTime;

    private Boolean isDeleted = false;
}
