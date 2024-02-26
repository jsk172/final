package com.khit.recruit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.khit.recruit.dto.ApplyDTO;
import com.khit.recruit.dto.JobDTO;
import com.khit.recruit.entity.Apply;
import com.khit.recruit.entity.Job;
import com.khit.recruit.repository.ApplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplyService {
	private final ApplyRepository applyRepository;
	
	//지원하기
	public void save(ApplyDTO applyDTO) {
		//dto -> entity로 변환
		Apply apply = Apply.toSaveEntity(applyDTO);
		//entity를 db에 저장
		applyRepository.save(apply);
	}
	
	//지원 리스트(일반회원)
	public Page<ApplyDTO> findListByMid(Pageable pageable, Long mid) {
		int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
		int pageSize = 5;
		pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "aid");
		
		Page<Apply> applyList = applyRepository.findByMember_Mid(pageable, mid);
		
		log.info("applyList.isFirst()=" + applyList.isFirst());
		log.info("applyList.isLast()=" + applyList.isLast());
		log.info("applyList.getNumber()=" + applyList.getNumber());
		
		// Page<Job>를 Page<JobDTO>로 변환
	    Page<ApplyDTO> applyDTOList = applyList.map(apply -> {
	        return new ApplyDTO(
	        		apply.getAid(),
	        		apply.getJob(),
	        		apply.getResume(),
	        		apply.getMember(),
	        		apply.getCompany()
	        );
	    });

	    return applyDTOList;
	}
	
	//지원자 리스트(기업회원)
	public Page<ApplyDTO> findListByCid(Pageable pageable, Long cid) {
		int page = pageable.getPageNumber() - 1; //db는 현재페이지보다 1 작아야함
		int pageSize = 5;
		pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "aid");
		
		Page<Apply> applyList = applyRepository.findByCompany_Cid(pageable, cid);
		
		log.info("applyList.isFirst()=" + applyList.isFirst());
		log.info("applyList.isLast()=" + applyList.isLast());
		log.info("applyList.getNumber()=" + applyList.getNumber());
		
		// Page<Job>를 Page<JobDTO>로 변환
	    Page<ApplyDTO> applyDTOList = applyList.map(apply -> {
	        return new ApplyDTO(
	        		apply.getAid(),
	        		apply.getJob(),
	        		apply.getResume(),
	        		apply.getMember(),
	        		apply.getCompany()
	        );
	    });

	    return applyDTOList;
	}

}
