package com.khit.library.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.khit.library.config.SecurityUser;
import com.khit.library.dto.EventDTO;
import com.khit.library.dto.MemberDTO;
import com.khit.library.service.EventService;
import com.khit.library.service.MemberService;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EventController {
	
    private final EventService eventService;
    private final MemberService memberService;

    // 이벤트 목록 페이지
    @GetMapping("/event/pagelist")
    public String eventPagelist(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @AuthenticationPrincipal SecurityUser principal,
            @RequestParam(value = "userAnswer", required = false) String userAnswer,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EventDTO> eventPage = eventService.paging(pageable);
        List<EventDTO> eventDTOList = eventService.findAll();

        // 퀴즈 생성 및 모델에 추가
        String quizQuestion = eventService.getQuizQuestionOrRetrieveStoredQuestion();
        String correctAnswer = eventService.getCorrectAnswer(quizQuestion);

        if (userAnswer != null) {
            // 사용자가 퀴즈에 대한 답을 제출한 경우 퀴즈 체크
            String quizResult = eventService.checkQuiz(userAnswer, correctAnswer);
            model.addAttribute("quizResult", quizResult);

            if (quizResult.startsWith("틀렸습니다.")) {
                // 사용자가 틀렸을 때의 추가 로직을 여기에 추가할 수 있습니다.
                // 예를 들어, 다른 메시지를 표시하거나 틀린 횟수를 증가시키는 등의 동작을 할 수 있습니다.
            }

            // 다음 퀴즈로 넘어가기
            eventService.generateAndStoreNewUniqueQuizQuestion();
            quizQuestion = eventService.getQuizQuestionOrRetrieveStoredQuestion();
            correctAnswer = eventService.getCorrectAnswer(quizQuestion);
        }

        model.addAttribute("quizQuestion", quizQuestion);
        model.addAttribute("eventPage", eventPage);
        model.addAttribute("eventList", eventDTOList);

        if(principal == null){
            return "event/pagelist";
        } else {
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "event/pagelist";
        }
    }

    // 이벤트 상세보기 페이지
    @GetMapping("/event/{evid}")
    public String getEventDetail(@PathVariable Long evid, Model model,
                                 @AuthenticationPrincipal SecurityUser principal) {
        eventService.updateHits(evid);
        EventDTO eventDTO = eventService.findById(evid);
        model.addAttribute("event", eventDTO);
        if(principal == null){
            return "event/detail";
        } else {
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "event/detail";
        }
    }

    // 이벤트 작성 페이지
    @GetMapping("/event/write")
    public String writeForm(@AuthenticationPrincipal SecurityUser principal, Model model) {
        if(principal == null){
            return "event/write";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "event/write";
        }
    }

    // 이벤트 작성 처리
    @PostMapping("/event/write")
    public String write(@ModelAttribute EventDTO eventDTO,
                        @AuthenticationPrincipal SecurityUser principal,
                        MultipartFile eventFile) throws IOException, Exception {
        eventDTO.setMember(principal.getMember());
        eventDTO.setEvhit(0);
        eventService.save(eventDTO, eventFile);

        return "redirect:/event/pagelist";
    }

    // 이벤트 수정 페이지
    @GetMapping("/event/update/{evid}")
    public String updateForm(@PathVariable Long evid, Model model,
                             @AuthenticationPrincipal SecurityUser principal) {
        EventDTO eventDTO = eventService.findById(evid);
        model.addAttribute("event", eventDTO);
        if(principal == null){
            return "event/update";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "event/update";
        }
    }

    // 이벤트 수정 처리
    @PostMapping("/event/update/{evid}")
    public String update(@ModelAttribute EventDTO eventDTO,
                        MultipartFile eventFile,
                        @AuthenticationPrincipal SecurityUser principal,
                        Model model) throws IOException, Exception {
        eventDTO.setMember(principal.getMember());
        EventDTO updatedEvent =  eventService.update(eventDTO, eventFile);
        model.addAttribute("event", updatedEvent);
        return "redirect:/event/" + eventDTO.getEvid();
    }

    // 이벤트 삭제
    @GetMapping("/event/delete/{evid}")
    public String deleteEvent(@PathVariable Long evid) {
        eventService.deleteById(evid);
        return "redirect:/event/pagelist";
    }
}