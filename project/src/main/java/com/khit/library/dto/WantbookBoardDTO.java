package com.khit.library.dto;

import java.sql.Timestamp;

import com.khit.library.entity.WantbookBoard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WantbookBoardDTO {
	
	private Long wbid; 			// 신청 번호
	private String wbphone1; 	// 연락처 1
	private String wbphone2; 	// 연락처 2
	private String wbphone3; 	// 연락처 3
	private String wbtitle; 	// 책 제목
	private String wbwrite; 	// 책 저자
	private String wbpublisher; // 출판사
	private String wbpubliyear; // 출판년도
	
	private Timestamp createdDate;   // 신청일
	
    // 추가: wbphone을 한 개의 문자열로 설정
    public void setWbphone(String wbphone) {
        String[] parts = wbphone.split("-");
        if (parts.length >= 3) {
            this.wbphone1 = parts[0];
            this.wbphone2 = parts[1];
            this.wbphone3 = parts[2];
        }
    }
	
	// entity -> dto
	public static WantbookBoardDTO toSaveDTO(WantbookBoard wantbookBoard) {
		WantbookBoardDTO wantbookBoardDTO = WantbookBoardDTO.builder().wbid(wantbookBoard.getWbid())
											              .wbphone1(wantbookBoard.getWbphone1())
											              .wbphone2(wantbookBoard.getWbphone2())
											              .wbphone3(wantbookBoard.getWbphone3())
														  .wbtitle(wantbookBoard.getWbtitle())
														  .wbwrite(wantbookBoard.getWbwrite())
														  .wbpublisher(wantbookBoard.getWbpublisher())
														  .wbpubliyear(wantbookBoard.getWbpubliyear())
														  .createdDate(wantbookBoard.getCreatedDate())
														  .build();
		return wantbookBoardDTO;
	}
}
