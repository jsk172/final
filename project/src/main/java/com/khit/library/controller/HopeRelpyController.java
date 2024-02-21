package com.khit.library.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khit.library.config.SecurityUser;
import com.khit.library.entity.HopeReply;
import com.khit.library.service.HopeReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HopeRelpyController {
	private final HopeReplyService hopeReplyService;

	@PostMapping("/hopereply/{hopeBoardHbid}")
	@ResponseBody
	public String insertReply(@PathVariable Long hopeBoardHbid,
							  @RequestBody HopeReply hopeReply,
							  @AuthenticationPrincipal SecurityUser principal
							  ) {
		hopeReply.setMember(principal.getMember());
		hopeReplyService.insertReply(hopeBoardHbid, hopeReply);
		
		return "댓글 등록 성공!";
	}
	
	@PutMapping("/hopereply/{hopeReplyHrid}")
	@ResponseBody
	public String updateReply(@PathVariable Long hopeReplyHrid, @RequestBody HopeReply hopeReply) {
		HopeReply updateHopeReply = hopeReplyService.findById(hopeReplyHrid);
		if(updateHopeReply == null) {
			return "수정할 댓글을 찾을 수 없습니다";
		}
		updateHopeReply.setHrcontent(hopeReply.getHrcontent()); // 새로운 내용으로 댓글 업데이트
	    hopeReplyService.save(updateHopeReply); // 업데이트된 댓글 저장
	    return "댓글 수정 완료!";
	}
	
	@DeleteMapping("/hopereply/{hopeReplyHrid}")
	@ResponseBody
	public String deleteReply(@PathVariable Long hopeReplyHrid) {
		hopeReplyService.deleteById(hopeReplyHrid);
		return "댓글 삭제 완료!";
	}
}