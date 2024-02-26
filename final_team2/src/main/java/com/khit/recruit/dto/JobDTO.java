package com.khit.recruit.dto;

import java.sql.Timestamp;

import com.khit.recruit.entity.CompanyEntity;
import com.khit.recruit.entity.Job;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobDTO {
	private Long jid; 			 	//구인공고 번호
	private String title; 			//제목
	private String occupation;   	//직종
	private String salary; 	 		//연봉
	private Integer minSalary; 	 	//최소연봉
	private Integer maxSalary; 	 	//최대연봉
	private String experience;	 	//경력
	private Integer minDuration;	//최소경력기간
	private Integer maxDuration;	//최대경력기간
	private String education;		//학력
	private String qualification;   //우대조건
	private String certification;	//면허,자격증
	private Integer recruitNum;		//모집인원
	private String empType;			//고용형태
	private String position;		//직급,직책
	private Integer postalCode;		//우편번호
	private String address01;		//도로명 주소
	private String address02;		//그외 주소
	private String day;				//근무요일
	private String startTimeWk;		//출근시간_평일
	private String endTimeWk;		//퇴근시간_평일
	private String startTimeWkE;	//출근시간_토요일
	private String endTimeWkE;		//퇴근시간_토요일
	private String workTimeEtc;		//출퇴근시간 기타
	private String duty;			//담당업무
	private String benefits;		//복리후생_연금,보험
	private String timeOff;			//복리후생_휴무,휴가, 행사
	private String amenities;		//복리후생_생활평의, 여가생활
	private String awards;			//복리후생_보상/수당/지원
	private String facilities;		//복리후생_시설지원
	private String startDate;	    //공고등록일
	private String endDate;			//공고마감일
	private String applyMtd;		//접수방법
	private String description;		//상세요강
	private String filename;		//상세요강_이미지
	private String filepath;	
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private Integer jobHits;		//조회수
	
	//CompanyDTO 추가
	private CompanyEntity company;
	
	//entity -> dto로 변환할 정적 메서드
	//db에 있는 모든 칼럼을 가져옴
	public static JobDTO toSaveDTO(Job job) {
		JobDTO jobDTO = JobDTO.builder()
				.jid(job.getJid())
				.title(job.getTitle())
				.occupation(job.getOccupation())
				.salary(job.getSalary())
				.minSalary(job.getMinSalary())
				.maxSalary(job.getMaxSalary())
				.experience(job.getExperience())
				.minDuration(job.getMinDuration())
				.maxDuration(job.getMaxDuration())
				.education(job.getEducation())
				.qualification(job.getQualification())
				.certification(job.getCertification())
				.recruitNum(job.getRecruitNum())
				.empType(job.getEmpType())
				.position(job.getPosition())
				.postalCode(job.getPostalCode())
				.address01(job.getAddress01())
				.address02(job.getAddress02())
				.day(job.getDay())
				.startTimeWk(job.getStartTimeWk())
				.endTimeWk(job.getEndTimeWk())
				.startTimeWkE(job.getStartTimeWkE())
				.endTimeWkE(job.getEndTimeWkE())
				.workTimeEtc(job.getWorkTimeEtc())
				.duty(job.getDuty())
				.benefits(job.getBenefits())
				.timeOff(job.getTimeOff())
				.amenities(job.getAmenities())
				.awards(job.getAwards())
				.facilities(job.getFacilities())
				.startDate(job.getStartDate())
				.endDate(job.getEndDate())
				.applyMtd(job.getApplyMtd())
				.description(job.getDescription())
				.filename(job.getFilename())
				.filepath(job.getFilepath())
				.createdDate(job.getCreatedDate())
				.updatedDate(job.getUpdatedDate())
				.jobHits(job.getJobHits())
				.company(job.getCompany())
				.build();
				
		return jobDTO;
	}
}
