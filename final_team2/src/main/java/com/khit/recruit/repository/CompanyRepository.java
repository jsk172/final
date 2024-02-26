package com.khit.recruit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khit.recruit.dto.CompanyDTO;
import com.khit.recruit.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>{
	Optional<CompanyEntity> findByCompanyId(String String);

	Optional<CompanyEntity> findByCid(Long cid);
}
