package com.khit.recruit.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.khit.recruit.config.SecurityCompany;
import com.khit.recruit.config.SecurityUser;
import com.khit.recruit.dto.ApplyDTO;
import com.khit.recruit.dto.JobDTO;
import com.khit.recruit.service.ApplyService;
import com.khit.recruit.service.JobService;
import com.khit.recruit.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ApplyController {
	
	private final ApplyService applyService;
	private final JobService jobService;
	
	//지원하기
	@PostMapping("/apply")
	public String apply(ApplyDTO applyDTO, 
			BindingResult bindingResult,
			@AuthenticationPrincipal SecurityCompany principal
			) {
		if(bindingResult.hasErrors()) {  //에러가 있으면 글쓰기폼으로 이동
			log.info("has errors.....");
			return "job/job_main";
		}
		log.info("applyDTO: " + applyDTO);
		//지원하기 처리
		applyService.save(applyDTO);
		
		return "redirect:/member/jopapp";
	}
	
	//지원 내역 리스트(일반 회원)
	@GetMapping("/member/jopapp")
	public String getResumePageList(
		@PageableDefault(page = 1) Pageable pageable,
		@AuthenticationPrincipal SecurityUser principal,
		Model model) {
		//로그인한 유저의 지원정보 받아오기
		Long mid = principal.getMember().getMid();

		Page<ApplyDTO> applyDTOList = null;
		//검색어가 없으면 페이지 처리를 하고, 검색어가 있으면 검색어로 페이지 처리
		applyDTOList = applyService.findListByMid(pageable, mid);
		log.info("applyDTOList : " + applyDTOList );
		
		//하단의 페이지 블럭 만들기
		int blockLimit = 10;  //하단에 보여줄 페이지 개수
		//시작 페이지 1, 11, 21    12/10 = 1.2 -> 2.2 -> 2-1, 1*10+1 =11
		int startPage = ((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit))-1)*blockLimit+1;
		//마지막 페이지 10, 20, 30 //12page -> 12 마지막
		int endPage = (startPage+blockLimit-1) > applyDTOList.getTotalPages() ?
				applyDTOList.getTotalPages() : startPage+blockLimit-1;
		endPage = Math.max(endPage, startPage); // 마지막 페이지는 시작 페이지와 같거나 큼
		
		model.addAttribute("applyList", applyDTOList);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "member/jopapp";
	}
	
	//지원자 리스트(기업 회원)
	@GetMapping("cpMypage/applicant")
		public String getApplicantPageList(
		@PageableDefault(page = 1) Pageable pageable,
		@AuthenticationPrincipal SecurityCompany principal,
		Model model) {
		
		//로그인한 유저의 지원정보 받아오기
		Long cid = principal.getCompany().getCid();
		
		Page<ApplyDTO> applyDTOList = null;
		//검색어가 없으면 페이지 처리를 하고, 검색어가 있으면 검색어로 페이지 처리
		applyDTOList = applyService.findListByCid(pageable, cid);
		log.info("applyDTOList : " + applyDTOList );
		
		//하단의 페이지 블럭 만들기
		int blockLimit = 10;  //하단에 보여줄 페이지 개수
		//시작 페이지 1, 11, 21    12/10 = 1.2 -> 2.2 -> 2-1, 1*10+1 =11
		int startPage = ((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit))-1)*blockLimit+1;
		//마지막 페이지 10, 20, 30 //12page -> 12 마지막
		int endPage = (startPage+blockLimit-1) > applyDTOList.getTotalPages() ?
				applyDTOList.getTotalPages() : startPage+blockLimit-1;
		endPage = Math.max(endPage, startPage); // 마지막 페이지는 시작 페이지와 같거나 큼
		
		model.addAttribute("applyList", applyDTOList);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "cpMypage/applicant";
	}
	
	//채팅 리스트
	@GetMapping("member/inquiry")
	public String getChatPageList(
		@PageableDefault(page = 1) Pageable pageable,
		@AuthenticationPrincipal SecurityCompany cprincipal,
		@AuthenticationPrincipal SecurityUser mprincipal,
		Model model) {
		
		Page<ApplyDTO> applyDTOList = null;
		
		if(cprincipal != null) {
			Long cid = cprincipal.getCompany().getCid();
			applyDTOList = applyService.findListByCid(pageable, cid);
		}
		if(mprincipal != null) {
			Long mid = mprincipal.getMember().getMid();
			applyDTOList = applyService.findListByMid(pageable, mid);
		}
		
		log.info("applyDTOList : " + applyDTOList );
		
		//하단의 페이지 블럭 만들기
		int blockLimit = 10;  //하단에 보여줄 페이지 개수
		//시작 페이지 1, 11, 21    12/10 = 1.2 -> 2.2 -> 2-1, 1*10+1 =11
		int startPage = ((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit))-1)*blockLimit+1;
		//마지막 페이지 10, 20, 30 //12page -> 12 마지막
		int endPage = (startPage+blockLimit-1) > applyDTOList.getTotalPages() ?
				applyDTOList.getTotalPages() : startPage+blockLimit-1;
		endPage = Math.max(endPage, startPage); // 마지막 페이지는 시작 페이지와 같거나 큼
		
		model.addAttribute("applyList", applyDTOList);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "member/info/inquiry";
	}
	
	
}
