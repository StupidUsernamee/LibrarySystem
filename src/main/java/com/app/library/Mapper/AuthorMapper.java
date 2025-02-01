package com.app.library.Mapper;

import com.app.library.DTO.AuthorDTO;
import com.app.library.Entity.Author;

public class AuthorMapper {

    public static Author mapToAuthorEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setNationality(authorDTO.getNationality());
        author.setGender(authorDTO.getGender());
        author.setEmail(authorDTO.getEmail());
        author.setPhone(authorDTO.getPhone());
        author.setIsTranslator(authorDTO.getIsTranslator());
        author.setIsDeleted(authorDTO.getIsDeleted());
        author.setAddress(authorDTO.getAddress());
        return author;
    }

    public static AuthorDTO mapToAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setNationality(author.getNationality());
        authorDTO.setGender(author.getGender());
        authorDTO.setEmail(author.getEmail());
        authorDTO.setPhone(author.getPhone());
        authorDTO.setIsTranslator(author.getIsTranslator());
        authorDTO.setIsDeleted(author.getIsDeleted());
        authorDTO.setAddress(author.getAddress());
        return authorDTO;
    }
}
