package com.app.library.Service;

import com.app.library.DTO.RowDTO;

import java.util.List;

public interface RowService {

    List<RowDTO> getRows();

    RowDTO getRowByRowNumber(int rowNumber);

    RowDTO addRow(RowDTO rowDTO);

    RowDTO updateRow(RowDTO rowDTO);

    int deleteRow(int rowNumber);
}
