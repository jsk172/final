package com.khit.recruit.dto;

import com.khit.recruit.entity.Apply;
import com.khit.recruit.entity.CompanyEntity;
import com.khit.recruit.entity.Job;
import com.khit.recruit.entity.MemberEntity;
import com.khit.recruit.entity.Resume;

import jakarta.persistence.Column;
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
public class ApplyDTO {
	
	private Long aid; 
	private Job job;
	private Resume resume;
	private MemberEntity member;
	private CompanyEntity company;
	
	public static ApplyDTO toSaveDTO(Apply apply) {
		ApplyDTO applyDTO = ApplyDTO.builder()
				.aid(apply.getAid())
				.job(apply.getJob())
				.resume(apply.getResume())
				.member(apply.getMember())
				.company(apply.getCompany())
				.build();
		return applyDTO;
	}

}
