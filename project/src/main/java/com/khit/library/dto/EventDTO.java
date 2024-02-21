package com.khit.library.dto;

import java.sql.Timestamp;

import com.khit.library.entity.Event;
import com.khit.library.entity.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDTO {

    private Long evid;
    
    private String evtitle;
    
    @NotBlank(message = "이벤트 내용은 비워둘 수 없습니다.")
    private String evcontent;

    private Timestamp createdDate;
    
    private Timestamp updatedDate;

    private Integer evhit;
    
    private String eventFilename;
    
    private String eventFilepath;

    private Member member;

    // DTO에서 엔티티로 변환하는 로직
    // Entity를 DTO로 변환
    public static EventDTO toSaveDTO(Event event) {
        return EventDTO.builder()
                .evid(event.getEvid())
                .member(event.getMember())
                .evtitle(event.getEvtitle())
                .evcontent(event.getEvcontent())
                .createdDate(event.getCreatedDate())
                .updatedDate(event.getUpdatedDate())
                .evhit(event.getEvhit())
                .eventFilename(event.getEventFilename())
                .eventFilepath(event.getEventFilepath())
                .build();
    }

    // DTO를 Entity로 변환 (저장)
    public static Event toSaveEntity(EventDTO eventDTO) {
        return Event.builder()
                .evid(eventDTO.getEvid())
                .evtitle(eventDTO.getEvtitle())
                .evcontent(eventDTO.getEvcontent())
                .evhit(0)  // 초기 조회수 0으로 설정
                .eventFilename(eventDTO.getEventFilename())
                .eventFilepath(eventDTO.getEventFilepath())
                .member(eventDTO.getMember())
                .build();
    }

    // DTO를 Entity로 변환 (수정)
    public static Event toUpdateEntity(EventDTO eventDTO) {
        return Event.builder()
                .evid(eventDTO.getEvid())
                .evtitle(eventDTO.getEvtitle())
                .evcontent(eventDTO.getEvcontent())
                .evhit(eventDTO.getEvhit() != null ? eventDTO.getEvhit() : 0)  // null 체크 후 초기화
                .eventFilename(eventDTO.getEventFilename())
                .eventFilepath(eventDTO.getEventFilepath())
                .member(eventDTO.getMember())
                .build();
    }
}
