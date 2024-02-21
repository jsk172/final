package com.khit.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khit.library.dto.BookDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "book")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId; // 도서번호

    @Column(nullable = false, length = 40)
    private String bname; //도서이름

    @Column(unique = true, length = 20)
    private String bnumber; //도서고유번호

    @Column(nullable = false, length = 30)
    private String author; //도서저자

    @Column(nullable = false)
    private String publisher; //출판사
    
    @Column(nullable = false)
    private String category; //책 카테고리

    @Column
    private String bfilename;
    @Column
    private String bfilepath;


    public static Book toSaveEntity(BookDTO bookDTO){
        Book book = Book.builder()
                .bname(bookDTO.getBname())
                .bnumber(bookDTO.getBnumber())
                .author(bookDTO.getAuthor())
                .publisher(bookDTO.getPublisher())
                .category(bookDTO.getCategory())
                .bfilename(bookDTO.getBfilename())
                .bfilepath(bookDTO.getBfilepath())
                .rentalReturnList(bookDTO.getRentalReturnList())
                .build();
        return book;
    }
    public static Book toUpdateEntity(BookDTO bookDTO){
        Book book = Book.builder()
                .bookId(bookDTO.getBookId())
                .bname(bookDTO.getBname())
                .bnumber(bookDTO.getBnumber())
                .author(bookDTO.getAuthor())
                .category(bookDTO.getCategory())
                .publisher(bookDTO.getPublisher())
                .bfilename(bookDTO.getBfilename())
                .bfilepath(bookDTO.getBfilepath())
                .rentalReturnList(bookDTO.getRentalReturnList())
                .build();
        return book;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn
    private Member member;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<RentalReturn> rentalReturnList = new ArrayList<>();
}