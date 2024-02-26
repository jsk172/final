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
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		//db에 있는 회원 정보를 조회하고
		//UserDetails 타입의 객체를 반환함
		Optional<MemberEntity> findMember = memberRepository.findByMemberId(memberId);
		if(findMember.isEmpty()) {
			throw new UsernameNotFoundException(memberId + "사용자 없음");
		}else {
			MemberEntity member = findMember.get();
			return new SecurityUser(member);
		}
	}
}
