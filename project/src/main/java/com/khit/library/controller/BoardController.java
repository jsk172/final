package com.khit.library.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.khit.library.config.SecurityUser;
import com.khit.library.dto.MemberDTO;
import com.khit.library.service.MemberService;
 
@Controller
@RequiredArgsConstructor
public class BoardController {
	private final MemberService memberService;
	
    @GetMapping("/board/useinfoboard")
    public String useinfo(@AuthenticationPrincipal SecurityUser principal, Model model){
        if(principal == null){
        	return "board/useinfoboard";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "board/useinfoboard";
        }
    }
    
    @GetMapping("/board/joininfoboard")
    public String joininfo(@AuthenticationPrincipal SecurityUser principal, Model model){
        if(principal == null){
        	return "board/joininfoboard";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "board/joininfoboard";
        }
    }

    @GetMapping("/board/datauseinfoboard")
    public String datainfo(@AuthenticationPrincipal SecurityUser principal, Model model){
    	if(principal == null){
    		return "board/datauseinfoboard";
    	}else{
    		MemberDTO memberDTO = memberService.findByMid(principal);
    		model.addAttribute("member", memberDTO);
    		return "board/datauseinfoboard";
    	}
    }
    
    @GetMapping("/board/howtocomeboard")
    public String howtocomeinfo(@AuthenticationPrincipal SecurityUser principal, Model model){
    	if(principal == null){
    		return "board/howtocomeboard";
    	}else{
    		MemberDTO memberDTO = memberService.findByMid(principal);
    		model.addAttribute("member", memberDTO);
    		return "board/howtocomeboard";
    	}
    }
    
    @GetMapping("/board/hopebookboard")
    public String hopebook(@AuthenticationPrincipal SecurityUser principal, Model model){
    	if(principal == null){
    		return "board/hopebookboard";
    	}else{
    		MemberDTO memberDTO = memberService.findByMid(principal);
    		model.addAttribute("member", memberDTO);
    		return "board/hopebookboard";
    	}
    }
    
    //자주하는질문 페이지
    @GetMapping("/faq/pagelist")
    public String faq(@AuthenticationPrincipal SecurityUser principal, Model model) {
    	if(principal == null){
    		return "faq/pagelist";
    	}else{
    		MemberDTO memberDTO = memberService.findByMid(principal);
    		model.addAttribute("member", memberDTO);
    		return "faq/pagelist";
    	}
    }
}
