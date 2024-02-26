package com.khit.recruit.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Optional;

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
@Entity
public class Job extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jid; 			 	//구인공고 번호
	
	@Column(nullable = false)
	private String title; 			//제목
	
	@Column(nullable = false)
	private String occupation;   	//직종
	
	@Column(nullable = false)
	private String salary; 	 	//연봉
	
	@Column
	private Integer minSalary; 	 	//최소연봉
	
	@Column
	private Integer maxSalary; 	 	//최대연봉
	
	@Column(nullable = false)
	private String experience;	 	// 경력
	 
	@Column
	private Integer minDuration;	//최소경력기간
	
	@Column
	private Integer maxDuration;	//최대경력기간
	
	@Column(nullable = false)
	private String education;		//학력
	
	@Column
	private String qualification;   //우대조건
	
	@Column
	private String certification;	//면허,자격증
	
	@Column(nullable = false)
	private Integer recruitNum;		//모집인원
	
	@Column(nullable = false)
	private String empType;			//고용형태
	
	@Column
	private String position;		//직급,직책
	
	@Column(nullable = false)
	private Integer postalCode;		//우편번호
	
	@Column(nullable = false)
	private String address01;		//도로명 주소
	
	@Column(nullable = false)
	private String address02;		//그외 주소
	
	@Column
	private String day;				//근무요일
	
	@Column
	private String startTimeWk;		//출근시간_평일
	
	@Column
	private String endTimeWk;		//퇴근시간_평일
	
	@Column
	private String startTimeWkE;	//출근시간_토요일
	
	@Column
	private String endTimeWkE;		//퇴근시간_토요일
	
	@Column
	private String workTimeEtc;		//출퇴근시간 기타
	
	@Column
	private String duty;			//담당업무
	
	@Column
	private String benefits;		//복리후생_연금,보험
	
	@Column
	private String timeOff;			//복리후생_휴무,휴가, 행사
	
	@Column
	private String amenities;		//복리후생_생활평의, 여가생사
	
	@Column
	private String awards;			//복리후생_보상/수당/지원
	
	@Column
	private String facilities;		//복리후생_시설지원
	
	private String startDate;		//공고등록일
	
	private String endDate;			//공고마감일
	
	@Column
	private String applyMtd;		//접수방법
	
	@Column(nullable = false)
	private String description;		//상세요강
	
	@Column
	private String filename;		//상세요강_이미지
	
	@Column
	private String filepath;	
	
	@Column
	private Integer jobHits;
	
	@ManyToOne
	@JoinColumn
	private CompanyEntity company;
	
	public static Job toSaveEntity(JobDTO jobDTO) {
		Job job = Job.builder()
				.title(jobDTO.getTitle())
				.occupation(jobDTO.getOccupation())
				.salary(jobDTO.getSalary())
				.minSalary(jobDTO.getMinSalary())
				.maxSalary(jobDTO.getMaxSalary())
				.experience(jobDTO.getExperience())
				.minDuration(jobDTO.getMinDuration())
				.maxDuration(jobDTO.getMaxDuration())
				.education(jobDTO.getEducation())
				.qualification(jobDTO.getQualification())
				.certification(jobDTO.getCertification())
				.recruitNum(jobDTO.getRecruitNum())
				.empType(jobDTO.getEmpType())
				.position(jobDTO.getPosition())
				.postalCode(jobDTO.getPostalCode())
				.address01(jobDTO.getAddress01())
				.address02(jobDTO.getAddress02())
				.day(jobDTO.getDay())
				.startTimeWk(jobDTO.getStartTimeWk())
				.endTimeWk(jobDTO.getEndTimeWk())
				.startTimeWkE(jobDTO.getStartTimeWkE())
				.endTimeWkE(jobDTO.getEndTimeWkE())
				.workTimeEtc(jobDTO.getWorkTimeEtc())
				.duty(jobDTO.getDuty())
				.benefits(jobDTO.getBenefits())
				.timeOff(jobDTO.getTimeOff())
				.amenities(jobDTO.getAmenities())
				.awards(jobDTO.getAwards())
				.facilities(jobDTO.getFacilities())
				.startDate(jobDTO.getStartDate())
				.endDate(jobDTO.getEndDate())
				.applyMtd(jobDTO.getApplyMtd())
				.description(jobDTO.getDescription())
				.filename(jobDTO.getFilename())
				.filepath(jobDTO.getFilepath())
				.jobHits(0)
				.company(jobDTO.getCompany())
				.build();
		return job;
	}
	
}
