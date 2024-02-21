package com.khit.library.controller;

import com.khit.library.config.SecurityUser;
import com.khit.library.dto.BookDTO;
import com.khit.library.dto.MemberDTO;
import com.khit.library.dto.RentalReturnDTO;
import com.khit.library.entity.Member;
import com.khit.library.service.BookService;
import com.khit.library.service.MemberService;
import com.khit.library.service.RentalReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rentalReturn")
public class RentalReturnController {
    private final RentalReturnService rentalReturnService;
    private final MemberService memberService;
    private final BookService bookService;

    //대출리스트
    @GetMapping("/list")
    public String getList(@AuthenticationPrincipal SecurityUser principal,Model model){
        List<RentalReturnDTO> rentalReturnDTOList = rentalReturnService.findAll();
        model.addAttribute("rentalList", rentalReturnDTOList);
        //model.addAttribute("rentalCount", rentalReturnService.count(memberId));
        model.addAttribute("able", rentalReturnService.rentalAble());

        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        if(principal == null){
            return "rental/list";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "rental/list";
        }
    }

    //도서대출
    @PostMapping("/rental")
    public @ResponseBody RentalReturnDTO rental(@RequestBody RentalReturnDTO rentalReturnDTO, @AuthenticationPrincipal SecurityUser user){
        System.out.println(rentalReturnDTO);
        rentalReturnDTO.setMember(Member.builder().memberId(user.getMember().getMemberId()).build());
        rentalReturnDTO.setReturnDate(null);
        rentalReturnService.save(rentalReturnDTO);

        return rentalReturnDTO;
    }

    //도서 반납
    @PutMapping("/return")
    public @ResponseBody RentalReturnDTO bookReturn(@RequestBody RentalReturnDTO rentalReturnDTO){
        RentalReturnDTO findRentalId = rentalReturnService.findByRentalId(rentalReturnDTO.getRentalId());
        findRentalId.setReturnDate(rentalReturnDTO.getReturnDate());
        rentalReturnService.update(findRentalId);
        return findRentalId;
    }

    //대출 베스트
    @GetMapping("/rentalbest")
    public String bestList(@AuthenticationPrincipal SecurityUser principal, Model model) {
        /*List<BookDTO> bestBooks = rentalReturnService.findOrderByRentalCount();
        model.addAttribute("bestBooks", bestBooks);*/
        if(principal == null){
            return "book/rentalbest";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "book/rentalbest";
        }
    }
}
