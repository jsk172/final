package com.khit.library.controller;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.khit.library.config.SecurityUser;
import com.khit.library.dto.MemberDTO;
import com.khit.library.dto.NoticeBoardDTO;
import com.khit.library.entity.NoticeBoard;
import com.khit.library.service.MemberService;
import com.khit.library.service.NoticeBoardService;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NoticeBoardController {
	
    private final NoticeBoardService noticeBoardService;
    private final MemberService memberService;

	//쓰기 페이지
    @GetMapping("notice/write")
    public String writeForm(@AuthenticationPrincipal SecurityUser principal, Model model) {
        if(principal == null){
            return "notice/write";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "notice/write";
        }
	}

	//쓰기 처리
	@PostMapping("/notice/write")
	public String write(@ModelAttribute NoticeBoard noticeBoard,
						@AuthenticationPrincipal SecurityUser principal,
						MultipartFile noticeBoardFile) throws IOException, Exception {
		noticeBoard.setMember(principal.getMember());
		noticeBoard.setNbhit(0);
		noticeBoardService.save(noticeBoard, noticeBoardFile);

		return "redirect:/notice/pagelist";
	}
	
	//수정 페이지
	@GetMapping("/notice/update/{nbid}")
	public String updateForm(@PathVariable Long nbid, Model model, @AuthenticationPrincipal SecurityUser principal) {
		NoticeBoardDTO noticeBoardDTO = noticeBoardService.findById(nbid);
		model.addAttribute("noticeBoard", noticeBoardDTO);
        if(principal == null){
            return "notice/update";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "notice/update";
        }
	}
	
	//수정 처리
	@PostMapping("/notice/update/{nbid}")
	public String update(@ModelAttribute NoticeBoardDTO noticeBoard,
						MultipartFile noticeBoardFile,
						@AuthenticationPrincipal SecurityUser principal,
    		            Model model) throws IOException, Exception {
    	noticeBoard.setMember(principal.getMember());
    	NoticeBoardDTO upNoticeBoard =  noticeBoardService.update(noticeBoard, noticeBoardFile);
    	model.addAttribute("noticeBoard", upNoticeBoard);
		return "redirect:/notice/" + noticeBoard.getNbid();
	}

	//페이징, 글 목록
    @GetMapping("/notice/pagelist")
    public String pagelist(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @AuthenticationPrincipal SecurityUser principal,
            Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<NoticeBoardDTO> noticeBoardPage = noticeBoardService.paging(pageable);
        List<NoticeBoardDTO> noticeBoardDTOList = noticeBoardService.findAll();
        model.addAttribute("noticeBoardPage", noticeBoardPage);
        model.addAttribute("noticeBoardList", noticeBoardDTOList);
        if(principal == null){
            return "notice/pagelist";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "notice/pagelist";
        }
    }
	
    //상세보기
    @GetMapping("/notice/{nbid}")
    public String getDetail(@PathVariable Long nbid, Model model,
    						@AuthenticationPrincipal SecurityUser principal) {
    	noticeBoardService.updateHits(nbid);
    	NoticeBoardDTO noticeBoardDTO = noticeBoardService.findById(nbid);
    	model.addAttribute("noticeBoard", noticeBoardDTO);
        if(principal == null){
            return "notice/detail";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "notice/detail";
        }
    }
	//삭제
	@GetMapping("/notice/delete/{nbid}")
	public String deleteHopeBoard(@PathVariable Long nbid) {
    	noticeBoardService.deleteById(nbid);
    	return "redirect:/notice/pagelist";
    }

    @GetMapping("/notice/main") @ResponseBody
    public List<NoticeBoard> mainList(){
        return noticeBoardService.mainList();
    }
			
}