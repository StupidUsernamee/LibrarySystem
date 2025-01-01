package com.app.library.Service;


import com.app.library.DTO.ShelfDTO;

import java.util.List;

public interface ShelfService {

    List<ShelfDTO> getAllShelves();

    ShelfDTO getShelvesByShelfNumber(int ShelfNumber);

    ShelfDTO addShelf(ShelfDTO shelfDTO);

    ShelfDTO updateShelf(ShelfDTO shelfDTO);

    int deleteShelf(int ShelfNumber);








}
