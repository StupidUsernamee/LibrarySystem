package com.app.library.Repository;

import com.app.library.Entity.Member_type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTypeRepository extends JpaRepository<Member_type, Long> {
}
