package com.app.library.Service.Impl;

import com.app.library.DTO.AuthorDTO;
import com.app.library.Entity.Author;
import com.app.library.Mapper.AuthorMapper;
import com.app.library.Repository.AuthorRepository;
import com.app.library.Service.AuthorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final EntityManager entityManager;

    @Override
    public AuthorDTO addAuthor(AuthorDTO authorDTO) {
        Author author = AuthorMapper.mapToAuthorEntity(authorDTO);

        Author savedAuthor = authorRepository.save(author);

        return AuthorMapper.mapToAuthorDTO(savedAuthor);
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            return null;
        }
        if (author.get().getIsDeleted()) {
            return null;
        }

        return AuthorMapper.mapToAuthorDTO(author.get());
    }

    @Override
    public List<AuthorDTO> getAuthors() {
        List<Author> authors = authorRepository.findAll();

        List<AuthorDTO> authorDTOs = new ArrayList<>(authors.stream().map(AuthorMapper::mapToAuthorDTO).toList());

        authorDTOs.removeIf(AuthorDTO::getIsDeleted);

        return authorDTOs;
    }

    @Override
    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
        Optional<Author> author = authorRepository.findById(authorDTO.getId());
        if (author.isEmpty()) {
            return null;
        }
        if (author.get().getIsDeleted()) {
            return null;
        }
        Author authorToUpdate = AuthorMapper.mapToAuthorEntity(authorDTO);
        updateEntityFromDTO(authorDTO, authorToUpdate);
        Author savedAuthor = authorRepository.save(authorToUpdate);
        return AuthorMapper.mapToAuthorDTO(savedAuthor);
    }

    private void updateEntityFromDTO(AuthorDTO authorDTO, Author author) {
        if (authorDTO.getFirstName() != null) {
            author.setFirstName(authorDTO.getFirstName());
        }
        if (authorDTO.getLastName() != null) {
            author.setLastName(authorDTO.getLastName());
        }
        if (authorDTO.getEmail() != null) {
            author.setEmail(authorDTO.getEmail());
        }
        if (authorDTO.getPhone() != null) {
            author.setPhone(authorDTO.getPhone());
        }
        if (authorDTO.getAddress() != null) {
            author.setAddress(authorDTO.getAddress());
        }
        if (authorDTO.getNationality() != null) {
            author.setNationality(authorDTO.getNationality());
        }
    }

    @Override
    public int deleteAuthor(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            return -1;
        }
        if (author.get().getIsDeleted()) {
            return 0;
        }
        Author authorToDelete = author.get();
        authorToDelete.setIsDeleted(true);
        Author savedAuthor = authorRepository.save(authorToDelete);
        return 1;
    }

    @Override
    public List<AuthorDTO> getAuthorByCriteria(String firstName, String lastName, String nationality) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);

        List<Predicate> predicates = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.like(root.get("firstName"), "%" + firstName + "%"));
        }

        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.like(root.get("lastName"), "%" + lastName + "%"));
        }
        if (nationality != null && !nationality.isEmpty()) {
            predicates.add(cb.like(root.get("nationality"), "%" + nationality + "%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        List<Author> authors = entityManager.createQuery(cq).getResultList();

        authors.removeIf(Author::getIsDeleted);

        return authors.stream()
                .map(AuthorMapper::mapToAuthorDTO)
                .toList();

    }
}
