package com.khit.recruit.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.khit.recruit.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long>{

	Page<Job> findByTitleContaining(String keyword, Pageable pageable);

	@Query("SELECT j FROM Job j JOIN j.company c WHERE c.cname LIKE %:cname%")
    Page<Job> findByCnameContaining(@Param("cname") String keyword, Pageable pageable);

	Page<Job> findByAddress01Containing(String city, Pageable pageable);

	Optional<Job> findByJid(Long jid);

	Page<Job> findByCompany_Cid(Pageable pageable, Long cid);


}
