package com.khit.recruit.entity;

import com.khit.recruit.dto.ApplyDTO;
import com.khit.recruit.dto.JobDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Apply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long aid; 
	
	@ManyToOne
	@JoinColumn
	private Job job;

	@ManyToOne
	@JoinColumn
	private Resume resume;
	
	@ManyToOne
	@JoinColumn
	private MemberEntity member;	//지원자 번호(회원 번호)
	
	@ManyToOne
	@JoinColumn
	private CompanyEntity company;
	
	//entity로 저장
	public static Apply toSaveEntity(ApplyDTO applyDTO) {
		Apply apply = Apply.builder()
				.aid(applyDTO.getAid())
				.job(applyDTO.getJob())
				.resume(applyDTO.getResume())
				.member(applyDTO.getMember())
				.company(applyDTO.getCompany())
				.build();
		return apply;
	}
	
}
