package com.khit.recruit.entity;

import java.sql.Timestamp;

import com.khit.recruit.dto.MemberDTO;

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
public class MemberEntity extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mid;
	
	@Column(nullable = false, unique = true , length = 30)
	private String memberId;
	
	@Column(nullable = false, length = 100)
	private String mpasswd;
	
	@Column(nullable = false, length = 30)
	private String mname;
	
	@Column(nullable = false)
	private String gender;
	
	@Column(nullable = true)
	private Integer birth;
	
	@Column(nullable = false, length = 30)
	private String phone;
	
	@Column(nullable = true)
	private String email;
	
	@Column
	private String filename;
	
	@Column
	private String filepath;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
		MemberEntity member = MemberEntity.builder()
				.memberId(memberDTO.getMemberId())
				.mpasswd(memberDTO.getMpasswd())
				.mname(memberDTO.getMname())
				.gender(memberDTO.getGender())
				.birth(memberDTO.getBirth())
				.phone(memberDTO.getPhone())
				.email(memberDTO.getEmail())
				.filename(memberDTO.getFilename())
				.filepath(memberDTO.getFilepath())
				.role(memberDTO.getRole())
				.build();
		
		return member;
	}
	
	public static MemberEntity toSaveUpdate(MemberDTO memberDTO) {
		MemberEntity member = MemberEntity.builder()
				.mid(memberDTO.getMid())
				.memberId(memberDTO.getMemberId())
                .mpasswd(memberDTO.getMpasswd())
				.mname(memberDTO.getMname())
				.gender(memberDTO.getGender())
				.birth(memberDTO.getBirth())
				.phone(memberDTO.getPhone())
				.email(memberDTO.getEmail())
				.filename(memberDTO.getFilename())
				.filepath(memberDTO.getFilepath())
				.role(memberDTO.getRole())
				.build();
		
		return member;
	}

	
}
