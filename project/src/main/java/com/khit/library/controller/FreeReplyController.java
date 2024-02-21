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
import com.khit.library.entity.FreeReply;
import com.khit.library.service.FreeReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FreeReplyController {
	private final FreeReplyService freeReplyService;

	@PostMapping("/freereply/{freeBoardFbid}")
	@ResponseBody
	public String insertReply(@PathVariable Long freeBoardFbid,
							  @RequestBody FreeReply freeReply,
							  @AuthenticationPrincipal SecurityUser principal
							  ) {
		freeReply.setMember(principal.getMember());
		freeReplyService.insertReply(freeBoardFbid, freeReply);
		
		return "댓글 등록 성공!";
	}
	
	@PutMapping("/freereply/{freeReplyFrid}")
	@ResponseBody
	public String updateReply(@PathVariable Long freeReplyFrid, @RequestBody FreeReply freeReply) {
		FreeReply updateFreeReply = freeReplyService.findById(freeReplyFrid);
		if(updateFreeReply == null) {
			return "수정할 댓글을 찾을 수 없습니다";
		}
		updateFreeReply.setFrcontent(freeReply.getFrcontent()); // 새로운 내용으로 댓글 업데이트
	    freeReplyService.save(updateFreeReply); // 업데이트된 댓글 저장
	    return "댓글 수정 완료!";
	}
	
	@DeleteMapping("/freereply/{freeReplyFrid}")
	@ResponseBody
	public String deleteReply(@PathVariable Long freeReplyFrid) {
		freeReplyService.deleteById(freeReplyFrid);
		return "댓글 삭제 완료!";
	}
}