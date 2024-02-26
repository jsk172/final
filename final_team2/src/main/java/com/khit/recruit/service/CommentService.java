package com.khit.recruit.service;

import com.khit.recruit.entity.Comment;
import com.khit.recruit.entity.Resume;
import com.khit.recruit.repository.CommentRepository;
import com.khit.recruit.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

//	public void resumeSave(Resume resume, MultipartFile reviewFile) throws IllegalStateException, IOException {
//		if (reviewFile != null && !reviewFile.isEmpty()) {
//			UUID uuid = UUID.randomUUID();  //무작위 아이디 생성(중복파일의 이름을 생성해줌)
//			String filename = uuid + "_" + reviewFile.getOriginalFilename();  //원본 파일
//			String filepath = "C:/springfiles/" + filename;
//
//			File savedFile = new File(filepath); //실제 파일
//			reviewFile.transferTo(savedFile);
//
//			//2. 파일 이름은 db에 저장
//			resume.setFilename(filename);
//			resume.setFilepath(filepath);
//
//		}
//		resumeRepository.save(resume);
//	}

    public void commentSave(Comment comment) {
        commentRepository.save(comment);
    }

    public Page<Comment> findFreeComments(Long id, Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");

        Page<Comment> commentList = commentRepository.findByFree_Id(id, pageable);
        return commentList;
    }

    public Page<Comment> findQnaComments(Long id, Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");

        Page<Comment> commentList = commentRepository.findByQna_Id(id, pageable);
        return commentList;
    }

    public Page<Comment> findNotiComments(Long id, Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");

        Page<Comment> commentList = commentRepository.findByNoti_Id(id, pageable);
        return commentList;
    }

    public Page<Comment> findReviewComments(Long id, Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");

        Page<Comment> commentList = commentRepository.findByReview_Id(id, pageable);
        return commentList;
    }

    public void deleteComment(Long commentId) {
        // optional 문법 findById로 찾아서 .ifPresent 메서드로 Comment가 있으면 삭제처리 한다는 뜻
        commentRepository.findById(commentId)
            .ifPresent(comment -> commentRepository.delete(comment));
    }

    // 여기 @Transactional을 선언한 이유는 Jpa 영속성 컨텍스트를 통해 더티체킹(업데이트)하기 위해
    // 만약 해당 어노테이션 사용하기 싫으면 제거하고 아래 주석된 로직을 주석 제거하고 위에 로직을 지우시면 됩니다.
    @Transactional
    public Comment update(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다"));
        comment.setContent(content);
        return comment;

//        Comment comment = commentRepository.findById(commentId)
//            .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다"));
//        comment.setContent(content);
//        return commentRepository.save(comment);
    }

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
