package com.khit.library.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khit.library.dto.NoticeBoardDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "noticeboard")
public class NoticeBoard extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long nbid; // 공지사항 글 번호

	@Column(nullable = false)
	private String nbtitle; // 공지사항 제목

	@Column(length = 2000, nullable = false)
	private String nbcontent; // 공지사항 내용

	@Column
	private Integer nbhit; // 조회수
	
	@Column
	private String noticeFilename;
	@Column
	private String noticeFilepath;

    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)	//글쓴이 - 외래
	@JoinColumn(name = "mid")
	private Member member;

	// dto -> entity
	//insert
	public static NoticeBoard toSaveEntity(NoticeBoardDTO noticeBoardDTO) {
		NoticeBoard noticeBoard = NoticeBoard.builder().nbid(noticeBoardDTO.getNbid())
				.nbtitle(noticeBoardDTO.getNbtitle()).nbcontent(noticeBoardDTO.getNbcontent())
				.nbhit(0)
				.noticeFilename(noticeBoardDTO.getNoticeFilename())
				.noticeFilepath(noticeBoardDTO.getNoticeFilepath())
				.member(noticeBoardDTO.getMember())
				.build();

		return noticeBoard;
	}
	
	//update
	public static NoticeBoard toUpdateEntity(NoticeBoardDTO noticeBoardDTO) {
		NoticeBoard noticeBoard = NoticeBoard.builder().nbid(noticeBoardDTO.getNbid())
				.nbtitle(noticeBoardDTO.getNbtitle()).nbcontent(noticeBoardDTO.getNbcontent())
				.nbhit(noticeBoardDTO.getNbhit())
				.noticeFilename(noticeBoardDTO.getNoticeFilename())
				.noticeFilepath(noticeBoardDTO.getNoticeFilepath())
				.member(noticeBoardDTO.getMember())
				.build();

		return noticeBoard;
	}
}
