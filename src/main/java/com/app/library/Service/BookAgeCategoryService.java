package com.app.library.Service;

import com.app.library.DTO.BookAgeCategoryDTO;
import com.app.library.Entity.BookAgeCategory;

import java.util.List;

public interface BookAgeCategoryService {

    BookAgeCategoryDTO addBookAgeCategory(BookAgeCategoryDTO bookAgeCategoryDTO);

    BookAgeCategoryDTO getBookAgeCategoryById(Long id);

    List<BookAgeCategoryDTO> getAllBookAgeCategory();

    BookAgeCategoryDTO getBookAgeCategoryByTitle(String title);

    BookAgeCategoryDTO updateBookAgeCategory(BookAgeCategoryDTO bookAgeCategoryDTO);

    int deleteBookAgeCategory(Long id);





}
