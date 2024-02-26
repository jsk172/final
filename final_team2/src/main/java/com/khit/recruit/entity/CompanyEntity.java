package com.khit.recruit.entity;

import java.sql.Timestamp;

import com.khit.recruit.dto.CompanyDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class CompanyEntity extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cid;
	
	@Column(length = 15)
	private String brNum;
	
	@Column(nullable = false, unique = true,  length = 30)
	private String companyId;
	
	@Column(nullable = false, length = 100)
	private String cpasswd;
	
	@Column(nullable = false, length = 30)
	private String ownername;
	
	@Column(nullable = false, length = 16)
	private String ownerphone;
	
	@Column(nullable = false, length = 30)
	private String cname;
	
	@Column(length = 30)
	private String tel;
	
	@Column(nullable = true)
	private String roadAddress;
	
	@Column(nullable = true)
	private String detailAddress;
	
	@Column(nullable = true, length = 30)
	private String postalcode;
	
	@Column(nullable = true)
	private String email;
	
	@Column
	private String filename;
	
	@Column
	private String filepath;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	public static CompanyEntity toSaveEntity(CompanyDTO companyDTO) {
		CompanyEntity company = CompanyEntity.builder()
				.brNum(companyDTO.getBrNum())
				.companyId(companyDTO.getCompanyId())
				.cpasswd(companyDTO.getCpasswd())
				.ownername(companyDTO.getOwnername())
				.ownerphone(companyDTO.getOwnerphone())
				.cname(companyDTO.getCname())
				.tel(companyDTO.getTel())
				.roadAddress(companyDTO.getRoadAddress())
				.detailAddress(companyDTO.getDetailAddress())
				.postalcode(companyDTO.getPostalcode())
				.email(companyDTO.getEmail())
				.filename(companyDTO.getFilename())
				.filepath(companyDTO.getFilepath())
				.role(companyDTO.getRole())
				.build();
		
		return company;
	}


	public static CompanyEntity toSaveUpdate(CompanyDTO companyDTO) {
		CompanyEntity company = CompanyEntity.builder()
				.cid(companyDTO.getCid())
				.brNum(companyDTO.getBrNum())
				.companyId(companyDTO.getCompanyId())
				.cpasswd(companyDTO.getCpasswd())
				.ownername(companyDTO.getOwnername())
				.ownerphone(companyDTO.getOwnerphone())
				.cname(companyDTO.getCname())
				.tel(companyDTO.getTel())
				.roadAddress(companyDTO.getRoadAddress())
				.detailAddress(companyDTO.getDetailAddress())
				.postalcode(companyDTO.getPostalcode())
				.email(companyDTO.getEmail())
				.filename(companyDTO.getFilename())
				.filepath(companyDTO.getFilepath())
				.role(companyDTO.getRole())
				.build();
		
		return company;
	}
}
