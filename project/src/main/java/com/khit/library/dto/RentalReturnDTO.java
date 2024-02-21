package com.khit.library.dto;

import com.khit.library.entity.Book;
import com.khit.library.entity.Member;
import com.khit.library.entity.RentalReturn;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class RentalReturnDTO {
    private Long rentalId;
    private Timestamp rentalDate;
    private Timestamp returnDate;
    private Timestamp deadlineDate;
    private Book book;
    private Member member;


    public static RentalReturnDTO toSaveDTO(RentalReturn rentalReturn){
        RentalReturnDTO rentalReturnDTO = RentalReturnDTO.builder()
                .rentalId(rentalReturn.getRentalId())
                .rentalDate(rentalReturn.getRentalDate())
                .returnDate(rentalReturn.getReturnDate())
                .deadlineDate(new Timestamp(rentalReturn.getRentalDate().getTime() + 7 * 24 * 60 * 60 * 1000)) // rentalDate + 7일로 설정
                .member(rentalReturn.getMember())
                .book(rentalReturn.getBook())
                .build();
        return rentalReturnDTO;
    }

}
