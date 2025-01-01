package com.app.library.Service;


import com.app.library.DTO.HallDTO;
import com.app.library.Entity.Hall;

import java.util.List;

public interface HallService {

    HallDTO createHall(HallDTO hallDTO);

    List<HallDTO> getAllHalls();

    HallDTO updateHall(HallDTO hallDTO);

    int deleteHall(long hallNumber);

    HallDTO getHallByNumber(Long hallNumber);
}
