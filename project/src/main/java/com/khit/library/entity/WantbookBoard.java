package com.khit.library.entity;

import com.khit.library.dto.WantbookBoardDTO;

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
@Table(name="wantbook")
public class WantbookBoard extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wbid; 			// 신청번호
	
	@Column
	private String wbphone1;    // 연락처 1
	
	@Column
	private String wbphone2;    // 연락처 2
	
	@Column
	private String wbphone3;    // 연락처 3
	
	@Column(nullable = false)
	private String wbtitle; 	// 책 제목
	
	@Column(nullable = false)
	private String wbwrite;		// 책 저자
	
	@Column(nullable = false)
	private String wbpublisher; // 출판사
	
	@Column(nullable = false)
	private String wbpubliyear; // 출판년도
	
	// dto -> entity
	// insert
	public static WantbookBoard toSaveEntity(WantbookBoardDTO wantbookBoardDTO) {
		WantbookBoard wantbookBoard = WantbookBoard.builder().wbid(wantbookBoardDTO.getWbid())
												.wbphone1(wantbookBoardDTO.getWbphone1())
												.wbphone2(wantbookBoardDTO.getWbphone2())
												.wbphone3(wantbookBoardDTO.getWbphone3())
												 .wbtitle(wantbookBoardDTO.getWbtitle())
												 .wbwrite(wantbookBoardDTO.getWbwrite())
												 .wbpublisher(wantbookBoardDTO.getWbpublisher())
												 .wbpubliyear(wantbookBoardDTO.getWbpubliyear())
												 .build();
		return wantbookBoard;
	}
	
    // 연락처가 널일 경우 빈 문자열 반환
    public String getWbphone1() {
        return wbphone1 != null ? wbphone1 : "";
    }

    public String getWbphone2() {
        return wbphone2 != null ? wbphone2 : "";
    }

    public String getWbphone3() {
        return wbphone3 != null ? wbphone3 : "";
    }
}