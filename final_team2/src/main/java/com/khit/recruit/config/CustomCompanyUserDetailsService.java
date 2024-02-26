package com.khit.recruit.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.khit.recruit.entity.CompanyEntity;
import com.khit.recruit.entity.MemberEntity;
import com.khit.recruit.repository.CompanyRepository;
import com.khit.recruit.repository.MemberRepository;

@Service
public class CustomCompanyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public UserDetails loadUserByUsername(String companyId) throws UsernameNotFoundException {
		//db에 있는 회원 정보를 조회하고
		//UserDetails 타입의 객체를 반환함
		Optional<CompanyEntity> findCompany = companyRepository.findByCompanyId(companyId);
		if(findCompany.isEmpty()) {
			throw new UsernameNotFoundException(companyId + "사용자 없음");
		}else {
			CompanyEntity company = findCompany.get();
			return new SecurityCompany(company);
		}
	}
}
