package com.khit.library.dto;

import com.khit.library.entity.Book;
import com.khit.library.entity.RentalReturn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long bookId; // 도서번호
    private String bname; //도서이름
    private String bnumber; //도서고유번호
    private String author; //도서저자
    private String publisher; //출판사
    private String category; //카테고리

    private String bfilename;
    private String bfilepath;

    private List<RentalReturn> rentalReturnList;

    private Timestamp createdDate;
    private Timestamp updatedDate;

    public static BookDTO toSaveDTO(Book book){
        BookDTO bookDTO = BookDTO.builder()
                .bookId(book.getBookId())
                .bname(book.getBname())
                .bnumber(book.getBnumber())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .category(book.getCategory())
                .bfilename(book.getBfilename())
                .bfilepath(book.getBfilepath())
                .createdDate(book.getCreatedDate())
                .updatedDate(book.getUpdatedDate())
                .rentalReturnList(book.getRentalReturnList())
                .build();
        return bookDTO;
    }
}
