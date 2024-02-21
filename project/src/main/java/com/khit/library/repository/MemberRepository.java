package com.khit.library.repository;

import com.khit.library.entity.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMid(String string);

    Member findByEmail(String email);

    Member findByEmailAndMid(String email, String mid);
    
	//페이징 처리를 위한 메서드
	Page<Member> findAll(Pageable pageable);
    
}
