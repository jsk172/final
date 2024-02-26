package com.khit.recruit.controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.khit.recruit.config.SecurityCompany;
import com.khit.recruit.dto.CompanyDTO;
import com.khit.recruit.dto.JobDTO;
import com.khit.recruit.service.JobService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequestMapping("/job")
@RequiredArgsConstructor
@Controller
public class JobController {
	
	private final JobService jobService;

	@GetMapping("/job_detail")
	public String job_detailForm() {
		return "job/job_detail";
	}
	
	//공고글쓰기 페이지
	@GetMapping("/job_write")
	public String job_writeForm(JobDTO jobDTO) {
		return "job/job_write";
	}
	
	//글쓰기 처리
	@PostMapping("/write")
	public String write(JobDTO jobDTO, 
			BindingResult bindingResult,
			//jobFile 이 필수가 아님
			@RequestParam(name = "jobFile", required = false) MultipartFile jobFile,
			Authentication authentication) throws IllegalStateException, IOException {
		log.info("jobDTO : " + jobDTO);
		if(bindingResult.hasErrors()) {  //에러가 있으면 글쓰기폼으로 이동
			log.info("has errors.....");
			return "main";
		}
		//글쓰기 처리
		jobService.save(jobDTO, jobFile);
		/* return "redirect:/board/pagelist"; */
		return "redirect:/job/job_main";
	}
	
	//공고 메인페이지
	@GetMapping("/job_main")
	public String getPageList(
		@RequestParam(value="type", required = false) String type,
		@RequestParam(value="keyword", required = false) String keyword,
		@RequestParam(value="city", required = false) String city,
		@RequestParam(value="sort", required = false, defaultValue = "register") String sort,
		@PageableDefault(page = 1) Pageable pageable,
		Model model) {
		
		//검색어가 없으면 페이지 처리를 하고, 검색어가 있으면 검색어로 페이지 처리
		Page<JobDTO> jobDTOList = null;
		if (city != null && !city.isEmpty()) {
	        jobDTOList = jobService.findByAddress01Containing(city, pageable);
	    } else if (keyword != null) {
	        // 기존의 검색 로직 유지
	        if(type != null && type.equals("title")) {
	            jobDTOList = jobService.findByJobTitleContaining(keyword, pageable);
	        } else if(type != null && type.equals("cname")){
	            jobDTOList = jobService.findByCnameContaining(keyword, pageable);
	        } else {
	            jobDTOList = jobService.findListAll(pageable);
	        }
	    } else {
	        jobDTOList = jobService.findListAll(pageable);
	    }
		log.info("jobDTOList : " + jobDTOList );
		
		//하단의 페이지 블럭 만들기
		int blockLimit = 10;  //하단에 보여줄 페이지 개수
		//시작 페이지 1, 11, 21    12/10 = 1.2 -> 2.2 -> 2-1, 1*10+1 =11
		int startPage = ((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit))-1)*blockLimit+1;
		//마지막 페이지 10, 20, 30 //12page -> 12 마지막
		int endPage = (startPage+blockLimit-1) > jobDTOList.getTotalPages() ?
				jobDTOList.getTotalPages() : startPage+blockLimit-1;
		endPage = Math.max(endPage, startPage); // 마지막 페이지는 시작 페이지와 같거나 큼
		
		model.addAttribute("jobList", jobDTOList);
		model.addAttribute("type", type);    //검색 유형 보내기
		model.addAttribute("kw", keyword);   //검색어 보내기
		model.addAttribute("selectedCity", city); //선택된 도시 이름
		model.addAttribute("selectedSort", sort);  //정렬기준
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "job/job_main";
	}
	
	
	//글 상세보기
	@GetMapping("/{jid}")
	public String getJob_detail(
			@PageableDefault(page = 1) Pageable pageable,
			@PathVariable Long jid,
			Model model) {
		//조회수
		//jobService.updateHits(id);
		//글 상세보기
		JobDTO jobDTO = jobService.findByJid(jid);
		model.addAttribute("job", jobDTO);
		model.addAttribute("page", pageable.getPageNumber());
		log.info("jobDTO : " + jobDTO);
		return "job/job_detail";
	}
		
	
	//기업 마이페이지의 작성글 리스트
	@GetMapping("/jobList")
	public String jobList(
			@PageableDefault(page = 1) Pageable pageable,
			Model model,
			@AuthenticationPrincipal SecurityCompany principal
			) {
		Page<JobDTO> jobDTOList = null;
		
		//로그인한 유저의 작성글 받아오기
		Long cid = principal.getCompany().getCid();
		
		jobDTOList = jobService.findListByCid(pageable, cid);
		log.info("jobDTOList : " + jobDTOList );
		
		//하단의 페이지 블럭 만들기
		int blockLimit = 10;  //하단에 보여줄 페이지 개수
		//시작 페이지 1, 11, 21    12/10 = 1.2 -> 2.2 -> 2-1, 1*10+1 =11
		int startPage = ((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit))-1)*blockLimit+1;
		//마지막 페이지 10, 20, 30 //12page -> 12 마지막
		int endPage = (startPage+blockLimit-1) > jobDTOList.getTotalPages() ?
				jobDTOList.getTotalPages() : startPage+blockLimit-1;
		endPage = Math.max(endPage, startPage); // 마지막 페이지는 시작 페이지와 같거나 큼
		
		model.addAttribute("jobList", jobDTOList);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "cpMypage/jobList";
	}
	
	
}
