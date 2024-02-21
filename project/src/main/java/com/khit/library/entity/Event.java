package com.khit.library.entity;

import com.khit.library.dto.EventDTO;
import com.khit.library.dto.NoticeBoardDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evid;

    @Column(nullable = false)
    private String evtitle;

    @Column(length = 2000, nullable = false)
    private String evcontent;

    @Column
    private Integer evhit;

    @Column
    private String eventFilename;

    @Column
    private String eventFilepath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    private Member member;

    // DTO에서 엔티티로 변환하는 로직
    public static Event toSaveEntity(EventDTO eventDTO) {
    	Event event = Event.builder().evid(eventDTO.getEvid())
				.evtitle(eventDTO.getEvtitle()).evcontent(eventDTO.getEvcontent())
				.evhit(0)
				.eventFilename(eventDTO.getEventFilename())
				.eventFilepath(eventDTO.getEventFilepath())
				.member(eventDTO.getMember())
				.build();

		return event;
	}
	
	//update
	public static Event toUpdateEntity(EventDTO eventDTO) {
		Event event = Event.builder().evid(eventDTO.getEvid())
				.evtitle(eventDTO.getEvtitle()).evcontent(eventDTO.getEvcontent())
				.evhit(eventDTO.getEvhit())
				.eventFilename(eventDTO.getEventFilename())
				.eventFilepath(eventDTO.getEventFilepath())
				.member(eventDTO.getMember())
				.build();

		return event;
	}

}
