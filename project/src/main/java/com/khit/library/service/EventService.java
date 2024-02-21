package com.khit.library.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.khit.library.dto.EventDTO;
import com.khit.library.entity.Event;
import com.khit.library.repository.EventRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EventService {
	
	private String storedQuizQuestion;

    private final EventRepository eventRepository;

    public void save(EventDTO eventDTO, MultipartFile eventFile) throws Exception, IOException {
        if(!eventFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String eventFilename = uuid + "_" + eventFile.getOriginalFilename();
            
            //String eventFilepath ="C:/springfiles/" + eventFilename;
            String eventFilepath ="/Users/Healer/springfiles/" + eventFilename; // 희린 전용

            File savedEventFile = new File(eventFilepath); // 실제 저장된 파일
            eventFile.transferTo(savedEventFile);

            eventDTO.setEventFilename(eventFilename);
            eventDTO.setEventFilepath(eventFilepath); // 파일 경로 설정
        }
        Event event = Event.toSaveEntity(eventDTO);
        eventRepository.save(event);
    }

    public EventDTO findById(Long evid) {
        Optional<Event> findEvent = eventRepository.findById(evid);
        return EventDTO.toSaveDTO(findEvent.get());
    }

    public EventDTO update(EventDTO eventDTO, MultipartFile eventFile) throws Exception, IOException {
        if(!eventFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String eventFilename = uuid + "_" + eventFile.getOriginalFilename();
            //String eventFilepath ="C:/springfiles/" + eventFilename;
            String eventFilepath ="/Users/Healer/springfiles/" + eventFilename; // 희린 전

            File savedEventFile = new File(eventFilepath); // 실제 저장된 파일
            eventFile.transferTo(savedEventFile);

            eventDTO.setEventFilename(eventFilename);
            eventDTO.setEventFilepath(eventFilepath);
        } else {
            eventDTO.setEventFilename(findById(eventDTO.getEvid()).getEventFilename());
            eventDTO.setEventFilepath(findById(eventDTO.getEvid()).getEventFilepath());
        }
        Event event = Event.toUpdateEntity(eventDTO);
        eventRepository.save(event);
        return findById(eventDTO.getEvid());
    }

    public void deleteById(Long evid) {
        eventRepository.deleteById(evid);
    }
    
    public List<EventDTO> findAll() {
        List<Event> eventList = eventRepository.findAll(Sort.by(Sort.Direction.DESC, "evid"));
        List<EventDTO> eventDTOList = new ArrayList<>();
        
        for(Event event : eventList) {
            EventDTO eventDTO = EventDTO.toSaveDTO(event);
            eventDTOList.add(eventDTO);
        }
        
        return eventDTOList;
    }

    // 페이징
    public Page<EventDTO> paging(Pageable pageable) {
        Page<Event> eventPage = eventRepository.findAll(pageable);
        return eventPage.map(event -> EventDTO.toSaveDTO(event));
    }

    // 조회수
    @Transactional
    public void updateHits(Long evid) {
        eventRepository.updateHits(evid);
    }

	public String generateQuizQuestion() {
		 List<String> quizQuestions = Arrays.asList(
	                "아홉 번 죽을 뻔하다 한 번 살아난다는 뜻으로 죽을 고비를 여러 번 넘기고 간신히 목숨을 건진다는 말",
	                "괴로움이 다하면 달콤함이 온다는 뜻으로 고생 끝에 낙이 온다는 말",
	                "정도가 지나친 것은 부족한 것보다 못하다는 말",
	                "눈을 비비고 다시 본다는 뜻으로 상대방의 학문이나 재주가 깜짝 놀랄 만큼 발전했음을 이르는 말",
	                "많으면 많을수록 더욱 좋다는 말",
	                "큰 차이가 없이 거의 같고 조금 다르다는 말",
	                "먹을 가까이하면 검어진다는 뜻으로 좋지 못한 사람과 가까이 지내면 나쁜 것에 물들게 된다는 말",
	                "같은 병을 앓는 사람끼리 서로 가엾게 여긴다는 뜻으로 어려운 처지에 있는 사람끼리 서로 동정하고 공감한다는 말",
	                "문 앞이 시장을 이룬다는 뜻으로 권세가 있거나 부자가 되어 집 문 앞이 방문객으로 넘쳐난다는 말",
	                "백 번 쏘아 백 번 맞춘다는 뜻으로 모든 일이 계획대로 들어맞는다는 말"
	                // 추가적인 사자성어 퀴즈 문제들...
	        );
		 Random random = new Random();
	     int randomIndex = random.nextInt(quizQuestions.size());
	     return quizQuestions.get(randomIndex);
	}

	public String getCorrectAnswer(String quizQuestion) {
		if ("아홉 번 죽을 뻔하다 한 번 살아난다는 뜻으로 죽을 고비를 여러 번 넘기고 간신히 목숨을 건진다는 말".equals(quizQuestion)) {
            return "구사일생";
        }else if("괴로움이 다하면 달콤함이 온다는 뜻으로 고생 끝에 낙이 온다는 말".equals(quizQuestion)) {
        	return "고진감래";
        }else if("많으면 많을수록 더욱 좋다는 말".equals(quizQuestion)) {
        	return "다다익선";
        }else if("정도가 지나친 것은 부족한 것보다 못하다는 말".equals(quizQuestion)) {
        	return "과유불급";
        }else if("눈을 비비고 다시 본다는 뜻으로 상대방의 학문이나 재주가 깜짝 놀랄 만큼 발전했음을 이르는 말".equals(quizQuestion)) {
        	return "괄목상대";
        }else if("먹을 가까이하면 검어진다는 뜻으로 좋지 못한 사람과 가까이 지내면 나쁜 것에 물들게 된다는 말".equals(quizQuestion)) {
        	return "근묵자흑";
        }else if("큰 차이가 없이 거의 같고 조금 다르다는 말".equals(quizQuestion)) {
        	return "대동소이";
        }else if("같은 병을 앓는 사람끼리 서로 가엾게 여긴다는 뜻으로 어려운 처지에 있는 사람끼리 서로 동정하고 공감한다는 말".equals(quizQuestion)) {
        	return "동병상련";
        }else if("문 앞이 시장을 이룬다는 뜻으로 권세가 있거나 부자가 되어 집 문 앞이 방문객으로 넘쳐난다는 말".equals(quizQuestion)) {
        	return "문전성시";
        }else if("백 번 쏘아 백 번 맞춘다는 뜻으로 모든 일이 계획대로 들어맞는다는 말".equals(quizQuestion)) {
        	return "백발백중";
        }
        return "";
	}

	// 퀴즈 체크 메서드
	public String checkQuiz(String userAnswer, String correctAnswer) {
	    if (userAnswer != null && userAnswer.equals(correctAnswer)) {
	        return "정답입니다!";
	    } else if (userAnswer != null) {
	        return "틀렸습니다. 정답은 '" + correctAnswer + "' 입니다.";
	    } else {
	        return ""; // 퀴즈에 답이 입력되지 않은 경우
	    }
	}

 // 퀴즈 생성 및 현재 문제 가져오기
    public String getQuizQuestionOrRetrieveStoredQuestion() {
        if (storedQuizQuestion == null) {
            generateAndStoreNewQuizQuestion();
        }
        return storedQuizQuestion;
    }

    // 새로운 퀴즈 생성 및 저장
    public void generateAndStoreNewQuizQuestion() {
    	List<String> quizQuestions = Arrays.asList(
    			"아홉 번 죽을 뻔하다 한 번 살아난다는 뜻으로 죽을 고비를 여러 번 넘기고 간신히 목숨을 건진다는 말",
                "괴로움이 다하면 달콤함이 온다는 뜻으로 고생 끝에 낙이 온다는 말",
                "정도가 지나친 것은 부족한 것보다 못하다는 말",
                "눈을 비비고 다시 본다는 뜻으로 상대방의 학문이나 재주가 깜짝 놀랄 만큼 발전했음을 이르는 말",
                "많으면 많을수록 더욱 좋다는 말",
                "큰 차이가 없이 거의 같고 조금 다르다는 말",
                "먹을 가까이하면 검어진다는 뜻으로 좋지 못한 사람과 가까이 지내면 나쁜 것에 물들게 된다는 말",
                "같은 병을 앓는 사람끼리 서로 가엾게 여긴다는 뜻으로 어려운 처지에 있는 사람끼리 서로 동정하고 공감한다는 말",
                "문 앞이 시장을 이룬다는 뜻으로 권세가 있거나 부자가 되어 집 문 앞이 방문객으로 넘쳐난다는 말",
                "백 번 쏘아 백 번 맞춘다는 뜻으로 모든 일이 계획대로 들어맞는다는 말"
                // 추가적인 사자성어 퀴즈 문제들...
            );
            Random random = new Random();
            int randomIndex = random.nextInt(quizQuestions.size());
        storedQuizQuestion = generateQuizQuestion();
    }
    
    // 중복을 방지하면서 새로운 퀴즈 생성 및 저장
    public void generateAndStoreNewUniqueQuizQuestion() {
        String newQuizQuestion;
        do {
            newQuizQuestion = generateQuizQuestion();
        } while (newQuizQuestion.equals(storedQuizQuestion));

        storedQuizQuestion = newQuizQuestion;
    }
    
}
	
       