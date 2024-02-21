package com.khit.library.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khit.library.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true, name="mid")
    private String mid;

    @Column(nullable = false, length = 2000)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;
    
    /*@Column
    @Builder.Default
    private Integer rentalCount = 0;

    @Column
    @Builder.Default
    private Boolean rentalAble = true;*/
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy="member", cascade = CascadeType.ALL)
    private List<HopeBoard> hopeBoardList = new ArrayList<>();


    @OneToMany(mappedBy="member", cascade = CascadeType.ALL)
    private List<HopeReply> hopeReplyList;
    

    public static Member toSaveEntity(MemberDTO memberDTO){
        Member member = Member.builder()
                .mid(memberDTO.getMid())
                .password(memberDTO.getPassword())
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .role(memberDTO.getRole())
//                .rentalCount(memberDTO.getRentalCount())
//                .rentalAble(memberDTO.getRentalAble())
                .build();
        return member;
    }

    public static Member toUpdateEntity(MemberDTO memberDTO){
        Member member = Member.builder()
                .memberId(memberDTO.getMemberId())
                .mid(memberDTO.getMid())
                .password(memberDTO.getPassword())
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
//                .rentalCount(memberDTO.getRentalCount())
//                .rentalAble(memberDTO.getRentalAble())
                .role(memberDTO.getRole())
                .build();
        return member;
    }
    public static Member toEmailEntity(MemberDTO memberDTO){
        Member member = Member.builder()
                .memberId(memberDTO.getMemberId())
                .mid(memberDTO.getMid())
                .password(memberDTO.getPassword())
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
//                .rentalCount(memberDTO.getRentalCount())
//                .rentalAble(memberDTO.getRentalAble())
                .role(memberDTO.getRole())
                .build();
        return member;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Book> bookList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<RentalReturn> rentalReturnList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ReadingRoom> readingRoomList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FreeBoard> freeBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NoticeBoard> noticeBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FreeReply> freeReplyList = new ArrayList<>();

}

