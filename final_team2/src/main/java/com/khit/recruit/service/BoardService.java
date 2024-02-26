package com.khit.recruit.service;

import com.khit.recruit.entity.*;
import com.khit.recruit.repository.*;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.JsonHelper;
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
@Transactional
public class BoardService {

    private final NotiRepository notiRepository;

    private final FreeRepository freeRepository;

    private final QnaRepository qnaRepository;

    private final ReviewRepository reviewRepository;

//	public void save(JobDTO jobDTO, MultipartFile jobFile ) throws IllegalStateException, IOException {
//		//1. 파일을 서버에 저장하고,
//		if(jobFile != null && !jobFile.isEmpty()) {  //전달된 파일이 있으면
//			//저장 경로
//			//배포 후 해당 경로 못찾음..
//			//String filepath = "C:\\bootworks\\bootboard\\src\\main\\resources\\static\\upload\\";
//
//			UUID uuid = UUID.randomUUID();  //무작위 아이디 생성(중복파일의 이름을 생성해줌)
//			String filename = uuid + "_" + jobFile.getOriginalFilename();  //원본 파일
//			String filepath = "C:/springfiles/" + filename;
//
//			//File 클래스 객체 생성
//			File savedFile = new File(filepath); //실제 파일
//			jobFile.transferTo(savedFile);
//
//			//2. 파일 이름은 db에 저장
//			jobDTO.setFilename(filename);
//			jobDTO.setFilepath(filepath); //파일 경로 설정함
//		}
//
//		//dto -> entity로 변환
//		Job job = Job.toSaveEntity(jobDTO);
//		//entity를 db에 저장
//		jobRepository.save(job);
//	}

    //job_main : 주소(address01) 간소화하는 메서드
//	private String extractAddress(String fullAddress) {
//	    // 예시로, "서울 영등포구 선유로 1"에서 "서울 영등포구" 부분만 추출
//	    String[] parts = fullAddress.split(" ");
//	    if(parts.length >= 2) {
//	        // 첫 번째와 두 번째 부분만 합쳐서 반환
//	        return parts[0] + " " + parts[1];
//	    }
//	    return fullAddress; // 분할에 실패한 경우 원본 주소 반환
//	}


    public Page<Noti> findNotiListAll(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");

        Page<Noti> boardList = notiRepository.findAll(pageable);

        log.info("boardList.isFirst()=" + boardList.isFirst());
        log.info("boardList.isLast()=" + boardList.isLast());
        log.info("boardList.getNumber()=" + boardList.getNumber());

        return boardList;
    }

    public Page<Free> findFreeListAll(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");

        Page<Free> freeList = freeRepository.findAll(pageable);

        log.info("freeList.isFirst()=" + freeList.isFirst());
        log.info("freeList.isLast()=" + freeList.isLast());
        log.info("freeList.getNumber()=" + freeList.getNumber());

        return freeList;
    }

    public Page<Qna> findQnaListAll(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");

        Page<Qna> qnaList = qnaRepository.findAll(pageable);

        log.info("qnaList.isFirst()=" + qnaList.isFirst());
        log.info("qnaList.isLast()=" + qnaList.isLast());
        log.info("qnaList.getNumber()=" + qnaList.getNumber());

        return qnaList;
    }

    public Page<Review> findReviewListAll(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");

        Page<Review> reviewList = reviewRepository.findAll(pageable);

        log.info("reviewList.isFirst()=" + reviewList.isFirst());
        log.info("reviewList.isLast()=" + reviewList.isLast());
        log.info("reviewList.getNumber()=" + reviewList.getNumber());

        return reviewList;
    }

    public void reviewSave(Review review, MultipartFile reviewFile)
        throws IllegalStateException, IOException {
        if (reviewFile != null && !reviewFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();  //무작위 아이디 생성(중복파일의 이름을 생성해줌)
            String filename = uuid + "_" + reviewFile.getOriginalFilename();  //원본 파일
            String filepath = "C:/springfiles/" + filename;

            File savedFile = new File(filepath); //실제 파일
            reviewFile.transferTo(savedFile);

            //2. 파일 이름은 db에 저장
            review.setFilename(filename);
            review.setFilepath(filepath);

        }

        review.setLikes(0);
        review.setViews(0);

        reviewRepository.save(review);
    }

    public void notiSave(Noti noti) {
        notiRepository.save(noti);
    }

    public void freeSave(Free free) {
        freeRepository.save(free);
    }

    public void qnaSave(Qna qna) {
        qnaRepository.save(qna);
    }

    public void reviewSave(Review review) {
        reviewRepository.save(review);
    }

    public Free findFreeById(Long id) {
        return freeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    public Page<Free> findFreeListByKeyword(String keyword, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return freeRepository.findByTitleContaining(keyword, pageable);
    }


    public Qna findQnaById(Long id) {
        return qnaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    public Page<Qna> findQnaListByKeyword(String keyword, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return qnaRepository.findByTitleContaining(keyword, pageable);
    }

    public Noti findNotiById(Long id) {
        return notiRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    public Page<Noti> findNotiListByKeyword(String keyword, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return notiRepository.findByTitleContaining(keyword, pageable);
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    public Page<Review> findReviewListByKeyword(String keyword, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageSize = 10;
        pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return reviewRepository.findByTitleContaining(keyword, pageable);
    }

    // 여기도 동일하게 @Transactional 빼고 싶으면 마지막 문단 return review; 지우고 주석처리된 메서드 주석 풀면 됩니다.
    @Transactional
    public Review reviewUpdate(Long rBoardId, String content, MultipartFile reviewFile)
        throws IOException {
        Review review = reviewRepository.findById(rBoardId)
            .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
        if (reviewFile != null && !reviewFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();  //무작위 아이디 생성(중복파일의 이름을 생성해줌)
            String filename = uuid + "_" + reviewFile.getOriginalFilename();  //원본 파일
            String filepath = "C:/springfiles/" + filename;

            File savedFile = new File(filepath); //실제 파일
            reviewFile.transferTo(savedFile);

            //2. 파일 이름은 db에 저장
            review.setFilename(filename);
            review.setFilepath(filepath);

        }

        review.setContent(content);
        return review;
//		return reviewRepository.save(review);

    }

    // 해당 문법 어려우면 지우고 주석 풀어주세요
    public void deleteReview(Long rBoardId) {
        reviewRepository.findById(rBoardId).ifPresent(review -> {
            reviewRepository.delete(review);
        });
//        Optional<Review> reviewOptional = reviewRepository.findById(rBoardId);
//        if (reviewOptional.isPresent()) {
//            Review review = reviewOptional.get();
//            reviewRepository.delete(review);
//        }

    }

    // 마찬가지로 어노테이션 지우고 싶으면 return free지우고 아래 주석 풀어주세요
    @Transactional
    public Free freeUpdate(Long boardId, String title, String content) {
        Free free = freeRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        free.setTitle(title);
        free.setContent(content);
        return free;
//        return freeRepository.save(free);
    }

    @Transactional
    public Qna qnaUpdate(Long boardId, String title, String content) {
        Qna qna = qnaRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        qna.setTitle(title);
        qna.setContent(content);
        return qna;
//        return qnaRepository.save(qna);
    }

    @Transactional
    public Noti notiUpdate(Long boardId, String title, String content) {
        Noti noti = notiRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        noti.setTitle(title);
        noti.setContent(content);
        return noti;
//        return notiRepository.save(noti);
    }

    public void freeDelete(Long boardId) {
        Optional<Free> freeOptional = freeRepository.findById(boardId);
        if (freeOptional.isPresent()) {
            Free free = freeOptional.get();
            freeRepository.delete(free);
        }
    }

    public void qnaDelete(Long boardId) {
        qnaRepository.findById(boardId).ifPresent(qna -> qnaRepository.delete(qna));
    }

    public void notiDelete(Long boardId) {
        notiRepository.findById(boardId).ifPresent(noti -> notiRepository.delete(noti));
    }

    public void increaseViews(Review review) {
        review.increaseViews();
    }

    public void increaseViews(Noti noti) {
        noti.increaseViews();
    }

    public void increaseViews(Qna qna) {
        qna.increaseViews(qna);
    }

    public void increaseViews(Free free) {
        free.increaseViews();

    }

    public void freeLike(Long boardId) {
        Free free = freeRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        free.increaseLike();
    }

    public void qnaLike(Long boardId) {
        Qna qna = qnaRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        qna.increaseLike();
    }

    public void notiLike(Long boardId) {
        Noti noti = notiRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        noti.increaseLike();
    }

    public void reviewLike(Long boardId) {
        Review review = reviewRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        review.increaseLike();
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
