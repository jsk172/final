package com.khit.recruit.dto;

import java.sql.Timestamp;

import com.khit.recruit.entity.CompanyEntity;
import com.khit.recruit.entity.MemberEntity;
import com.khit.recruit.entity.Role;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
public class MemberDTO {
	
	private Long mid;
	private String memberId;
	private String mpasswd;
	private String mname;
	private String gender;
	private Integer birth;
	private String phone;
	private Timestamp mcreatedDate; 
	private String email;
	private String filename;
	private String filepath;
	private Role role;
	
	public static MemberDTO toSaveDTO(MemberEntity member) {
		MemberDTO memberDTO = MemberDTO.builder()
				.mid(member.getMid())
				.memberId(member.getMemberId())
				.mpasswd(member.getMpasswd())
				.mname(member.getMname())
				.gender(member.getGender())
				.birth(member.getBirth())
				.phone(member.getPhone())
				.role(member.getRole())
				.mcreatedDate(member.getCreatedDate())
				.email(member.getEmail())
				.filename(member.getFilename())
				.filepath(member.getFilepath())
				.build();
		
		return memberDTO;
	}
}
