package com.khit.recruit.config;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.khit.recruit.entity.MemberEntity;

public class SecurityUser extends User{
	
	private static final long serialVersionUID = 456L;
	
	private MemberEntity member;
	
	public SecurityUser(MemberEntity member) {
		//암호화 안된 상태는 {noop}을 사용함
		super(member.getMemberId(), member.getMpasswd(), //entity 요소들을 가져옴
				AuthorityUtils.createAuthorityList(member.getRole().toString()));
		this.member = member;
	}

	public MemberEntity getMember() {
		return member;
	}
	
}