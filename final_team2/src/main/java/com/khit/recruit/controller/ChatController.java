package com.khit.recruit.controller;

import org.hibernate.grammars.hql.HqlParser.IsEmptyPredicateContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.khit.recruit.config.SecurityCompany;
import com.khit.recruit.config.SecurityUser;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class ChatController {
    
   
    @GetMapping("/chat") 
    public String chat(
          Model model, 
          @AuthenticationPrincipal SecurityUser principal,
          @AuthenticationPrincipal SecurityCompany company){ 
       if(principal != null){
          String name = principal.getMember().getMname();
          model.addAttribute("name", name);
       }else {
          String name = company.getCompany().getCname();
          model.addAttribute("name", name);
       }
       return "chat"; 
   }
    
}