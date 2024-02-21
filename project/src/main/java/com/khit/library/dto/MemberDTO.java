package com.khit.library.dto;

import com.khit.library.entity.Member;
import com.khit.library.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class MemberDTO {
    private Long memberId;

    @NotEmpty(message = "ID는 필수 항목입니다.")
    @Size(min = 6)
    private String mid;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!*]).*$", message = "비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다.")
    private String password;

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "이름은 필수 항목입니다.")
    @Pattern(regexp = "^[가-힣]*$", message = "이름은 한글만 입력해 주세요.")
    private String name;

    private Role role;

    private Timestamp createdDate;
    private Timestamp updatedDate;


    public static MemberDTO toSaveDTO(Member member){
        MemberDTO memberDTO = MemberDTO.builder()
                .memberId(member.getMemberId())
                .mid(member.getMid())
                .password(member.getPassword())
                .email(member.getEmail())
                .name(member.getName())
//                .rentalCount(member.getRentalCount())
//                .rentalAble(member.getRentalAble())
                .role(member.getRole())
                .createdDate(member.getCreatedDate())
                .updatedDate(member.getUpdatedDate())
                .build();
        return memberDTO;
    }
}
