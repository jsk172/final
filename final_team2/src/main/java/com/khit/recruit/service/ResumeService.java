package com.khit.recruit.service;

import com.khit.recruit.entity.*;
import com.khit.recruit.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResumeService {
	
private final ResumeRepository resumeRepository;
	
	public void resumeSave(Resume resume, MultipartFile resumeFile) throws IllegalStateException, IOException {
		if (resumeFile != null && !resumeFile.isEmpty()) {
			UUID uuid = UUID.randomUUID();  //무작위 아이디 생성(중복파일의 이름을 생성해줌)
			String filename = uuid + "_" + resumeFile.getOriginalFilename();  //원본 파일
			String filepath = "C:/springfiles/" + filename;

			File savedFile = new File(filepath); //실제 파일
			resumeFile.transferTo(savedFile);

			//2. 파일 이름은 db에 저장
			resume.setFilename(filename);
			resume.setFilepath(filepath);

		}
		resumeRepository.save(resume);
	}

//	public void notiSave(Noti noti) {
//		notiRepository.save(noti);
//	}

//	public Page<Board> findListAllByType(String type, Pageable pageable) {
//		int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
//		int pageSize = 10;
//		pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
//
//		Page<Board> boardList = boardRepository.findAllByType(type, pageable);
//
//		log.info("boardList.isFirst()=" + boardList.isFirst());
//		log.info("boardList.isLast()=" + boardList.isLast());
//		log.info("boardList.getNumber()=" + boardList.getNumber());
//		return boardList;
//	}

//	public Page<JobDTO> findByJobTitleContaining(String keyword, Pageable pageable) {
//		int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
//		int pageSize = 10;
//		pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "jid");
//
//		Page<Job> jobList = jobRepository.findByTitleContaining(keyword, pageable);
//
//		// Page<Job>를 Page<JobDTO>로 변환
//	    Page<JobDTO> jobDTOList = jobList.map(job -> {
//	        // 각 Job의 CompanyEntity로부터 CompanyDTO를 조회
//	    	//CompanyDTO companyDTO = companyRepository.findByCid(job.getCompany().getCid());
//
//	        // JobDTO 생성, 이 때 companyDTO도 설정
//	        return new JobDTO(
//	            job.getJid(),
//	            job.getTitle(),
//	            job.getOccupation(),
//	            job.getSalary(),
//	            job.getMinSalary(),
//	            job.getMaxSalary(),
//	            job.getExperience(),
//	            job.getMinDuration(),
//	            job.getMaxDuration(),
//	            job.getEducation(),
//	            job.getQualification(),
//	            job.getCertification(),
//	            job.getRecruitNum(),
//	            job.getEmpType(),
//	            job.getPosition(),
//	            job.getPostalCode(),
//	            extractAddress(job.getAddress01()),
//	            job.getAddress02(),
//	            job.getDay(),
//	            job.getStartTimeWk(),
//	            job.getEndTimeWk(),
//	            job.getStartTimeWkE(),
//	            job.getEndTimeWkE(),
//	            job.getWorkTimeEtc(),
//	            job.getDuty(),
//	            job.getBenefits(),
//	            job.getTimeOff(),
//	            job.getAmenities(),
//	            job.getAwards(),
//	            job.getFacilities(),
//	            job.getStartDate(),
//	            job.getEndDate(),
//	            job.getApplyMtd(),
//	            job.getDescription(),
//	            job.getFilename(),
//	            job.getFilepath(),
//	            job.getCreatedDate(),
//	            job.getUpdatedDate(),
//	            job.getJobHits(),
//	            job.getCompany()
//	        );
//	    });
//
//	    return jobDTOList;
//	}


//	public Page<JobDTO> findByCnameContaining(String keyword, Pageable pageable) {
//		int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
//		int pageSize = 10;
//		pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "jid");
//
//		Page<Job> jobList = jobRepository.findByCnameContaining(keyword, pageable);
//
//		// Page<Job>를 Page<JobDTO>로 변환
//	    Page<JobDTO> jobDTOList = jobList.map(job -> {
//	        // 각 Job의 CompanyEntity로부터 CompanyDTO를 조회
//	    	//CompanyDTO companyDTO = companyRepository.findByCid(job.getCompany().getCid());
//
//	        // JobDTO 생성, 이 때 companyDTO도 설정
//	        return new JobDTO(
//	            job.getJid(),
//	            job.getTitle(),
//	            job.getOccupation(),
//	            job.getSalary(),
//	            job.getMinSalary(),
//	            job.getMaxSalary(),
//	            job.getExperience(),
//	            job.getMinDuration(),
//	            job.getMaxDuration(),
//	            job.getEducation(),
//	            job.getQualification(),
//	            job.getCertification(),
//	            job.getRecruitNum(),
//	            job.getEmpType(),
//	            job.getPosition(),
//	            job.getPostalCode(),
//	            extractAddress(job.getAddress01()),
//	            job.getAddress02(),
//	            job.getDay(),
//	            job.getStartTimeWk(),
//	            job.getEndTimeWk(),
//	            job.getStartTimeWkE(),
//	            job.getEndTimeWkE(),
//	            job.getWorkTimeEtc(),
//	            job.getDuty(),
//	            job.getBenefits(),
//	            job.getTimeOff(),
//	            job.getAmenities(),
//	            job.getAwards(),
//	            job.getFacilities(),
//	            job.getStartDate(),
//	            job.getEndDate(),
//	            job.getApplyMtd(),
//	            job.getDescription(),
//	            job.getFilename(),
//	            job.getFilepath(),
//	            job.getCreatedDate(),
//	            job.getUpdatedDate(),
//	            job.getJobHits(),
//	            job.getCompany()
//	        );
//	    });
//
//	    return jobDTOList;
//	}

//	public Page<JobDTO> findByAddress01Containing(String city, Pageable pageable) {
//		int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
//		int pageSize = 10;
//		pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "jid");
//
//		Page<Job> jobList = jobRepository.findByAddress01Containing(city, pageable);
//
//		// Page<Job>를 Page<JobDTO>로 변환
//	    Page<JobDTO> jobDTOList = jobList.map(job -> {
//	        // 각 Job의 CompanyEntity로부터 CompanyDTO를 조회
//	    	//CompanyDTO companyDTO = companyRepository.findByCid(job.getCompany().getCid());
//
//	        // JobDTO 생성, 이 때 companyDTO도 설정
//	        return new JobDTO(
//	            job.getJid(),
//	            job.getTitle(),
//	            job.getOccupation(),
//	            job.getSalary(),
//	            job.getMinSalary(),
//	            job.getMaxSalary(),
//	            job.getExperience(),
//	            job.getMinDuration(),
//	            job.getMaxDuration(),
//	            job.getEducation(),
//	            job.getQualification(),
//	            job.getCertification(),
//	            job.getRecruitNum(),
//	            job.getEmpType(),
//	            job.getPosition(),
//	            job.getPostalCode(),
//	            extractAddress(job.getAddress01()),
//	            job.getAddress02(),
//	            job.getDay(),
//	            job.getStartTimeWk(),
//	            job.getEndTimeWk(),
//	            job.getStartTimeWkE(),
//	            job.getEndTimeWkE(),
//	            job.getWorkTimeEtc(),
//	            job.getDuty(),
//	            job.getBenefits(),
//	            job.getTimeOff(),
//	            job.getAmenities(),
//	            job.getAwards(),
//	            job.getFacilities(),
//	            job.getStartDate(),
//	            job.getEndDate(),
//	            job.getApplyMtd(),
//	            job.getDescription(),
//	            job.getFilename(),
//	            job.getFilepath(),
//	            job.getCreatedDate(),
//	            job.getUpdatedDate(),
//	            job.getJobHits(),
//	            job.getCompany()
//	        );
//	    });
//
//	    return jobDTOList;
//	}

//	public JobDTO findByJid(Long jid) {
//		Optional<Job> findJob = jobRepository.findByJid(jid);
//		if(findJob.isPresent()) { //검색한 게시글이 있으면 dto를 컨트롤러로 반환함
//			JobDTO jobDTO = JobDTO.toSaveDTO(findJob.get());
//			return jobDTO;
//		}else { //게시글이 없으면 에러 처리
//			throw new BootBoardException("게시글을 찾을 수 없습니다.");
//		}
//	}

	
}
