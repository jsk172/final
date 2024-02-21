package com.khit.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.khit.library.dto.ReadingRoomDTO;
import com.khit.library.dto.RentalReturnDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name= "readingroom")
/*@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})*/
public class ReadingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readingId;

    @Column
    @CreationTimestamp
    private Timestamp enter; //입실

    @Column
    private Timestamp checkOut; //퇴실

    @Column(unique = true, nullable = false)
    private Integer seat; //좌석

    @Column
    @Builder.Default
    private boolean seatAvailable = true;

    public static ReadingRoom toSaveEntity(ReadingRoomDTO readingRoomDTO){
        Timestamp enterTimestamp = readingRoomDTO.getEnter() != null ? readingRoomDTO.getEnter() : new Timestamp(System.currentTimeMillis());
        ReadingRoom readingRoom = ReadingRoom.builder()
                .enter(enterTimestamp)
                .checkOut(new Timestamp(readingRoomDTO.getEnter().getTime() + (6 * 60 * 60 * 1000)))
                .seat(readingRoomDTO.getSeat())
                .seatAvailable(readingRoomDTO.isSeatAvailable())
                .member(readingRoomDTO.getMember())
                .build();
        return readingRoom;
    }

    public static ReadingRoom toUpdateEntity(ReadingRoomDTO readingRoomDTO){
        ReadingRoom readingRoom = ReadingRoom.builder()
                .readingId(readingRoomDTO.getReadingId())
                .enter(readingRoomDTO.getEnter())
                .checkOut(readingRoomDTO.getCheckOut())
                .seat(readingRoomDTO.getSeat())
                .seatAvailable(readingRoomDTO.isSeatAvailable())
                .member(readingRoomDTO.getMember())
                .build();
        return readingRoom;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @JsonIgnore
    private Member member;
}
