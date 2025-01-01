package com.app.library.Mapper;

import com.app.library.DTO.BookAgeCategoryDTO;
import com.app.library.Entity.BookAgeCategory;
import lombok.Data;

@Data
public class BookAgeCategoryMapper {

    public static BookAgeCategory mapToAgeCategoryEntity(BookAgeCategoryDTO ageCategoryDTO) {
        BookAgeCategory ageCategory = new BookAgeCategory();
        ageCategory.setId(ageCategoryDTO.getId());
        ageCategory.setTitle(ageCategoryDTO.getTitle());
        ageCategory.setStartAge(ageCategoryDTO.getStartAge());
        ageCategory.setEndAge(ageCategoryDTO.getEndAge());
        return ageCategory;
    }


    public static BookAgeCategoryDTO mapToBookAgeCategoryDTO(BookAgeCategory ageCategory) {
        BookAgeCategoryDTO bookAgeCategoryDTO = new BookAgeCategoryDTO();
        bookAgeCategoryDTO.setId(ageCategory.getId());
        bookAgeCategoryDTO.setTitle(ageCategory.getTitle());
        bookAgeCategoryDTO.setStartAge(ageCategory.getStartAge());
        bookAgeCategoryDTO.setEndAge(ageCategory.getEndAge());
        return bookAgeCategoryDTO;
    }
}
