package com.khit.library.dto;

import java.sql.Timestamp;

import com.khit.library.entity.Member;
import com.khit.library.entity.NoticeBoard;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeBoardDTO {
	
	private Long nbid;	//공지사항 글 번호
	
	private String nbtitle; //공지사항 제목
	
	@NotBlank(message = "공지사항 내용은 비워둘 수 없습니다.")
	private String nbcontent;	//공지사항 내용
	
	private Timestamp createdDate;	//작성일
	private Timestamp updatedDate;	//수정일
	
	private Integer nbhit;	//조회수
	
	private String noticeFilename;
	private String noticeFilepath;
	
	private Member member;	//작성자 - 외래

	//entity -> dto
	public static NoticeBoardDTO toSaveDTO(NoticeBoard noticeBoard) {
		NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.builder().nbid(noticeBoard.getNbid())
																.member(noticeBoard.getMember())
																.nbtitle(noticeBoard.getNbtitle())
																.nbcontent(noticeBoard.getNbcontent())
																.createdDate(noticeBoard.getCreatedDate())
																.updatedDate(noticeBoard.getUpdatedDate())
																.nbhit(noticeBoard.getNbhit())
																.noticeFilename(noticeBoard.getNoticeFilename())
																.noticeFilepath(noticeBoard.getNoticeFilepath())
																.build();
		return noticeBoardDTO;
	}
}
