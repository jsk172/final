package com.khit.recruit.dto;

import java.sql.Timestamp;

import com.khit.recruit.entity.CompanyEntity;
import com.khit.recruit.entity.Job;
import com.khit.recruit.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyDTO {

	private Long cid;
	private String brNum;
	private String companyId;
	private String cpasswd;
	private String ownername;
	private String ownerphone;
	private String cname;
	private String tel;
	private String roadAddress;
	private String detailAddress;
	private String postalcode;
	private Timestamp createdDate; 
	private String email;
	private String filename;
	private String filepath;
	private Role role;
	
	//entity -> dto로 변환할 정적 메서드
	//db에 있는 모든 칼럼을 가져옴
	public static CompanyDTO toSaveDTO(CompanyEntity companyEntity) {
		CompanyDTO companyDTO = CompanyDTO.builder()
				.cid(companyEntity.getCid())
				.brNum(companyEntity.getBrNum())
				.companyId(companyEntity.getCompanyId())
				.cpasswd(companyEntity.getCpasswd())
				.ownername(companyEntity.getOwnername())
				.ownerphone(companyEntity.getOwnerphone())
				.cname(companyEntity.getCname())
				.tel(companyEntity.getTel())
				.roadAddress(companyEntity.getRoadAddress())
				.detailAddress(companyEntity.getDetailAddress())
				.postalcode(companyEntity.getPostalcode())
				.createdDate(companyEntity.getCreatedDate())
				.email(companyEntity.getEmail())
				.filename(companyEntity.getFilename())
				.filepath(companyEntity.getFilepath())
				.role(companyEntity.getRole())
				.build();
		
		return companyDTO;
	}
}
