package com.khit.library.controller;

import com.khit.library.config.SecurityUser;
import com.khit.library.dto.MemberDTO;
import com.khit.library.dto.WantbookBoardDTO;
import com.khit.library.entity.WantbookBoard;

import com.khit.library.service.MemberService;
import com.khit.library.service.WantbookBoardService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WantbookBoardController {
    private final WantbookBoardService wantbookBoardService;
    private final MemberService memberService;

    // write page 글쓰기
    @GetMapping("/wantbook/write")
    public String writeForm(@AuthenticationPrincipal SecurityUser principal, Model model) {
        if(principal == null){
            return "wantbook/write";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "wantbook/write";
        }
    }
    
    // 글쓰기 처리
    @PostMapping("/wantbook/write")
    public String write(@ModelAttribute WantbookBoardDTO wantbookBoardDTO,
                        @AuthenticationPrincipal SecurityUser principal,
                        Model model) throws IOException, Exception {
        // 세 개의 연락처 값을 하나의 문자열로 합치기
        String wbphone = wantbookBoardDTO.getWbphone1() + "-" + wantbookBoardDTO.getWbphone2() + "-" + wantbookBoardDTO.getWbphone3();
        wantbookBoardDTO.setWbphone(wbphone);

        // DTO를 엔티티로 변환하여 저장
        WantbookBoard wantbookBoard = WantbookBoard.toSaveEntity(wantbookBoardDTO);
        wantbookBoardService.save(wantbookBoard);

        if (principal != null) {
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
        }
        return "redirect:/book/list";
    }

	// 글 목록
    @GetMapping("/wantbook/pagelist")
    public String pagelist(
            @AuthenticationPrincipal SecurityUser principal,
            Model model) {
        List<WantbookBoardDTO> wantbookBoardDTOList = wantbookBoardService.findAll();
        model.addAttribute("wantbookBoardList", wantbookBoardDTOList);
        if(principal == null){
            return "wantbook/pagelist";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "wantbook/pagelist";
        }
        //return "wantbook/pagelist";
    }


    // 글 삭제
    @PostMapping("/wantbook/delete/{wbid}")
    public String deleteHopeBoard(@PathVariable Long wbid) {
    	wantbookBoardService.deleteById(wbid);
    	return "redirect:/wantbook/pagelist";
    }
}
