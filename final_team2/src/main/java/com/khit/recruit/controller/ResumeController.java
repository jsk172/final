package com.khit.recruit.controller;

import com.khit.recruit.entity.Resume;
import com.khit.recruit.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/resume")
@RequiredArgsConstructor
@Controller
public class ResumeController {

	private final ResumeService resumeService;
	@GetMapping("/main")
	public String resumeMainForm(
			Model model) {
		model.addAttribute("resume", new Resume());
		return "resume/main";
	}

	@PostMapping("/write")
	public String resumeWritePost(
			Resume resume,
			BindingResult bindingResult,
			@RequestParam(name = "file", required = false) MultipartFile file
	) {
		log.info("resume : " + resume);

		if(bindingResult.hasErrors()) {
			log.info("has errors.....");
			return "resume/main";
		}
		try {
			resumeService.resumeSave(resume, file);
		} catch (Exception e) {
			log.info("resumeSave error....." + e.getMessage());
			return "resume/main";
		}

		return "redirect:/main";
	}

}
