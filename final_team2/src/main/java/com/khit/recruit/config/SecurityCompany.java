package com.khit.recruit.config;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.khit.recruit.entity.CompanyEntity;

public class SecurityCompany extends User{
	
	private static final long serialVersionUID = 457L;
	
	private CompanyEntity company;
	
	public SecurityCompany(CompanyEntity company) {
		super(company.getCompanyId(), company.getCpasswd(), 
				AuthorityUtils.createAuthorityList(company.getRole().toString()));
		this.company = company;
	}

	public CompanyEntity getCompany() {
		return company;
	}
}