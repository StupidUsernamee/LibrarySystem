package com.app.library.Repository;

import com.app.library.Entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    Library findByLibraryNumber(Long libraryNumber);


}
