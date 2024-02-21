package com.khit.library.dto;

import java.sql.Timestamp;
import java.util.List;

import com.khit.library.entity.HopeBoard;
import com.khit.library.entity.HopeReply;
import com.khit.library.entity.Member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FaqBoardDTO {
	
	private Long hbid;  // 바란다 게시판 글 번호
	
	private Member member;  // foreign key
	
	private String hbtitle;   // 글 제목
	private String hbcontent;   // 글 내용
	
	private Timestamp createdDate;   // 작성일
	private Timestamp updatedDate;   // 수정일
	
	private Integer hbhit;    // 조회수
	
	private String hopeFilename;
	private String hopeFilepath;
	
	private List<HopeReply> hopeReplyList;
	
	// entity -> dto
	public static FaqBoardDTO toSaveDTO(HopeBoard hopeBoard) {
		FaqBoardDTO hopeBoardDTO = FaqBoardDTO.builder().hbid(hopeBoard.getHbid())
														  .member(hopeBoard.getMember())
														  .hbtitle(hopeBoard.getHbtitle())
														  .hbcontent(hopeBoard.getHbcontent())
														  .createdDate(hopeBoard.getCreatedDate())
														  .updatedDate(hopeBoard.getUpdatedDate())
														  .hbhit(hopeBoard.getHbhit())
														  .hopeFilename(hopeBoard.getHopeFilename())
														  .hopeFilepath(hopeBoard.getHopeFilepath())
														  .hopeReplyList(hopeBoard.getHopeReplyList())
														  .build();
		return hopeBoardDTO;
	}
}
