package com.khit.library.dto;

import com.khit.library.entity.Member;
import com.khit.library.entity.ReadingRoom;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ReadingRoomDTO {
    private Long readingId;
    private Timestamp enter; //입실
    private Timestamp checkOut; //퇴실
    private Integer seat; //좌석
    private boolean seatAvailable = true; //이용가능여부

    private Member member;

    public static ReadingRoomDTO toSaveDTO(ReadingRoom readingRoom){
//        Timestamp enterTimestamp = readingRoom.getEnter() != null ? readingRoom.getEnter() : new Timestamp(System.currentTimeMillis());
//        Timestamp checkOutTimestamp = new Timestamp(enterTimestamp.getTime() + (6 * 60 * 60 * 1000));
        ReadingRoomDTO readingRoomDTO = ReadingRoomDTO.builder()
                .readingId(readingRoom.getReadingId())
                .enter(readingRoom.getEnter())
                .checkOut(readingRoom.getCheckOut())
                .seat(readingRoom.getSeat())
                .seatAvailable(readingRoom.isSeatAvailable())
                .member(readingRoom.getMember())
                .build();
        return readingRoomDTO;
    }
}
