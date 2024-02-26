package com.khit.recruit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.khit.recruit.entity.Apply;

public interface ApplyRepository extends JpaRepository<Apply, Long>{

	Page<Apply> findByMember_Mid(Pageable pageable, Long mid);

	Page<Apply> findByCompany_Cid(Pageable pageable, Long cid);

}
