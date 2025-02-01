package com.app.library.Service;

import com.app.library.DTO.AuthorDTO;

import java.util.List;

public interface AuthorService {

    public AuthorDTO addAuthor(AuthorDTO authorDTO);

    public AuthorDTO getAuthor(Long id);

    public List<AuthorDTO> getAuthors();

    public AuthorDTO updateAuthor(AuthorDTO authorDTO);

    public int deleteAuthor(Long id);

    public List<AuthorDTO> getAuthorByCriteria(String firstName, String lastName, String nationality);
}
