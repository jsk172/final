package com.khit.recruit.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.khit.recruit.config.SecurityCompany;
import com.khit.recruit.config.SecurityUser;
import com.khit.recruit.dto.CompanyDTO;
import com.khit.recruit.dto.MemberDTO;
import com.khit.recruit.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	
	private final MemberService memberService;

	@GetMapping("/main")
	public String mainForm(
			@AuthenticationPrincipal SecurityUser mprincipal, 
			@AuthenticationPrincipal SecurityCompany cprincipal, 
			Model model) {
		
		//member 회원이 로그인하였으면 memberDTO 정보 보내기
		if (mprincipal != null) {
	        MemberDTO memberDTO = memberService.findByMid(mprincipal.getMember().getMid());
	        model.addAttribute("member", memberDTO);
	    }
	    //compnay 회원이 로그인하였으면 companyDTO 정보 보내기
	    if (cprincipal != null) {
	        CompanyDTO companyDTO = memberService.findByCId(cprincipal.getCompany().getCid());
	        model.addAttribute("company", companyDTO);
	    }
		
		return "main";
	}

}
