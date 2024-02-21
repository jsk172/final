package com.khit.library.dto;

import java.sql.Timestamp;
import java.util.List;

import com.khit.library.entity.FreeBoard;
import com.khit.library.entity.FreeReply;
import com.khit.library.entity.Member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreeBoardDTO {
	
	private Long fbid;  //자유게시판 번호
	
	private Member member;  // foreign key
	
	private String fbtitle;  //자유게시판 제목
	
	private String fbcontent; //자유게시판 내용
	
	private Integer fbhit;  //조회수
	
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	private String freeFilename;
	private String freeFilepath;
	
	private List<FreeReply> freeReplyList;
	
	//entity -> dto
	public static FreeBoardDTO toSaveDTO(FreeBoard freeBoard) {
		FreeBoardDTO freeBoardDTO = FreeBoardDTO.builder()
				.fbid(freeBoard.getFbid())
				.member(freeBoard.getMember())
				.fbtitle(freeBoard.getFbtitle())
				.fbcontent(freeBoard.getFbcontent())
				.fbhit(freeBoard.getFbhit())
				.freeFilename(freeBoard.getFreeFilename())
				.freeFilepath(freeBoard.getFreeFilepath())
				.createdDate(freeBoard.getCreatedDate())
				.updatedDate(freeBoard.getUpdatedDate())
				.freeReplyList(freeBoard.getFreeReplyList())
				.build();
		
		return freeBoardDTO;
	}
	
}
