package com.khit.library.controller;

import com.khit.library.config.SecurityUser;
import com.khit.library.dto.MemberDTO;
import com.khit.library.dto.RentalReturnDTO;
import com.khit.library.entity.Member;
import com.khit.library.service.EmailService;
import com.khit.library.service.MemberService;
import com.khit.library.service.RentalReturnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final RentalReturnService rentalReturnService;
    private final EmailService emailService;


    //헤더 로그인 맴버
    @GetMapping("/")
    public String main(Model model, @AuthenticationPrincipal SecurityUser principal){
        if(principal == null){
            return "index";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "index";
        }
    }


    //회원가입 폼
    @GetMapping("/member/join0")
    public String joinForm0(MemberDTO memberDTO){
        return "member/join0";
    }
    @GetMapping("/member/join1")
    public String joinForm(MemberDTO memberDTO){
        return "member/join1";
    }
    @GetMapping("/member/join2")
    public String joinForm2(MemberDTO memberDTO){
        return "member/join2";
    }
    //회원가입 처리
    @PostMapping("/member/join1")
    public String join(@Valid MemberDTO memberDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "member/join1";
        }
        memberService.save(memberDTO);
        return "redirect:/member/join2";
    }
    //로그인 폼
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }
    //로그아웃
    @GetMapping("/logout")
    public String logout(){
        return "redirect:/";
    }
    //회원목록
    @GetMapping("/member/list")
    public String getList(@AuthenticationPrincipal SecurityUser principal, Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        if(principal == null){
            return "member/list";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            model.addAttribute("rental", rentalReturnService.count(memberDTO.getMemberId()));
            model.addAttribute("able", rentalReturnService.rentalAble());
            return "member/list";
        }
    }
    @GetMapping("/member/{memberId}")
    public String getMember(@AuthenticationPrincipal SecurityUser principal, @PathVariable Long memberId, Model model){

        if(principal == null){
            return "member/detail";
        }else{
            MemberDTO memberDTO = memberService.findById(memberId);
            model.addAttribute("member", memberDTO);
            model.addAttribute("rental", rentalReturnService.count(memberId));
            model.addAttribute("able", rentalReturnService.rentalAble());
            return "member/detail";
        }
    }

    //회원삭제
    @GetMapping("/member/delete/{memberId}")
    public String delete(@PathVariable Long memberId){
        memberService.deleteById(memberId);
        return "redirect:/member/list";
    }

    //회원 탈퇴 폼
    @GetMapping("/member/withdrawal/{memberId}")
    public String withdrawalForm(@AuthenticationPrincipal SecurityUser principal, @PathVariable Long memberId, Model model, MemberDTO memberDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors() || principal == null){
            return "member/withdrawal";
        }else {
            memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "member/withdrawal";
        }
    }
    //회원 탈퇴 처리
    @PostMapping("/member/withdrawal")
    public String Withdrawal(MemberDTO memberDTO,@RequestParam String password, Authentication authentication, RedirectAttributes redirectAttributes){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean result = memberService.withdrawal(userDetails.getUsername(), password);
        if(result){
            return "redirect:/logout";
        }else{
            redirectAttributes.addFlashAttribute("wrongPassword", "비밀번호가 일치하지 않습니다.");
            return "redirect:/member/withdrawal/" + memberDTO.getMemberId();
        }
    }

    //아이디 찾기
    @GetMapping("/member/searchId")
    public String searchForm(){
        return "member/searchid";
    }
    @PostMapping("/member/searchId")
    public String searchId(@RequestParam("email") String email, Model model){
        String foundId = memberService.findIdByEmail(email);
        if(foundId != null){
            model.addAttribute("foundId", foundId);
            return "member/searchresult";
        }else{
            model.addAttribute("notFound", "찾으시는 아이디가 없습니다.");
            return "member/searchid";
        }
    }
    //비밀번호 재설정
    @GetMapping("/member/resetPassword")
    public String resetForm(MemberDTO memberDTO){
        return "member/resetpassword";
    }
    @GetMapping("/member/resetPassword2")
    public String resetForm2(){
        return "member/resetpassword2";
    }
    @PostMapping("/member/resetPassword")
    public String resetPassword(MemberDTO memberDTO, Model model){
        boolean isMatch = memberService.isEmailAndMidMatch(memberDTO.getEmail(), memberDTO.getMid());
        if(isMatch){
            String temporaryPassword = memberService.generateTemporaryPassword();
            emailService.sendTemporaryPassword(memberDTO.getEmail(), temporaryPassword);
            //임시 비밀번호를 DB에 저장
            MemberDTO memberDTO2 = memberService.findByMid2(memberDTO.getMid());
            memberDTO2.setPassword(temporaryPassword);
            memberService.update(memberDTO2);
            return "redirect:/member/resetPassword2";
        }else{
            model.addAttribute("error", "일치하는 정보가 없습니다.");
            return "member/resetpassword";
        }
    }

    //회원수정 폼
    @GetMapping("/member/update/{memberId}")
    public String updateForm(@AuthenticationPrincipal SecurityUser principal, @PathVariable Long memberId, Model model,
                             MemberDTO memberDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors() || principal == null){
            return "member/update";
        }else {
            memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            model.addAttribute("rental", rentalReturnService.count(memberId));
            model.addAttribute("able", rentalReturnService.rentalAble());
            return "member/update";
        }
    }

    //회원수정 처리
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO, Model model,  @AuthenticationPrincipal SecurityUser principal, BindingResult bindingResult){
        memberService.update(memberDTO);
        log.info("dto : " + memberDTO);

        if(bindingResult.hasErrors()){
            return "member/update";
        }
        memberDTO = memberService.findByMid(principal);
        model.addAttribute("member", memberDTO);
        return "redirect:/member/update/" + memberDTO.getMemberId();
    }


    //아이디 중복검사
    @PostMapping("/member/check-id")
    public @ResponseBody String checkId(@RequestParam("mid") String mid){
        String resultText = memberService.checkId(mid);
        return resultText;
    }

    //나의 대출목록
    @GetMapping("/member/rentallist")
    public String rentalList(@AuthenticationPrincipal SecurityUser principal, Model model){
        String mid = principal.getMember().getMid();

        List<RentalReturnDTO> rentalReturnDTOList = rentalReturnService.findByMemberMid(mid);

        model.addAttribute("rentalList", rentalReturnDTOList);

        if(principal == null){
            return "member/rentallist";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            model.addAttribute("rental", rentalReturnService.count(memberDTO.getMemberId()));
            model.addAttribute("able", rentalReturnService.rentalAble());
            return "member/rentallist";
        }
    }
}

