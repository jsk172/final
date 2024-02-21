package com.khit.library.entity;

import java.util.List;

import com.khit.library.dto.HopeBoardDTO;

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
@Table(name="hopeboard")
public class HopeBoard extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hbid; // 바란다 게시판 번호
	
	@Column(nullable = false)
	private String hbtitle; // 글 제목
	
	@Column(length = 2000, nullable = false)
	private String hbcontent; // 글 내용
	
	@Column
	private Integer hbhit;  // 조회수
	
	@Column
	private String hopeFilename;
	@Column
	private String hopeFilepath;
	
	
	@ManyToOne(fetch = FetchType.LAZY)  // 글쓴이 - 외래키
	@JoinColumn(name="mid")
	private Member member;
	
	@OneToMany(mappedBy="hopeboard", cascade = CascadeType.ALL)
	@OrderBy("hrid desc")
	private List<HopeReply> hopeReplyList;
	
	// dto -> entity
	// insert
	public static HopeBoard toSaveEntity(HopeBoardDTO hopeBoardDTO) {
		HopeBoard hopeBoard = HopeBoard.builder().hbtitle(hopeBoardDTO.getHbtitle())
												 .hbcontent(hopeBoardDTO.getHbcontent())
												 .hbhit(0)
												 .hopeFilename(hopeBoardDTO.getHopeFilename())
												 .hopeFilepath(hopeBoardDTO.getHopeFilepath())
												 .member(hopeBoardDTO.getMember())
												 .build();
		return hopeBoard;
	}
	
	// update
	public static HopeBoard toUpdateEntity(HopeBoardDTO hopeBoardDTO) {
		HopeBoard hopeBoard = HopeBoard.builder().hbid(hopeBoardDTO.getHbid())  // id 필요
												 .hbtitle(hopeBoardDTO.getHbtitle())
												 .hbcontent(hopeBoardDTO.getHbcontent())
												 .hbhit(hopeBoardDTO.getHbhit())
												 .hopeFilename(hopeBoardDTO.getHopeFilename())
												 .hopeFilepath(hopeBoardDTO.getHopeFilepath())
												 .member(hopeBoardDTO.getMember())
												 .build();
		return hopeBoard;
	}
}