package com.khit.recruit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khit.recruit.entity.CompanyEntity;
import com.khit.recruit.entity.MemberEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
	Optional<MemberEntity> findByMemberId(String String);

	Optional<MemberEntity> findByMid(Long mid);

    //회원 수정
    @Query("update MemberEntity set email=:email, phone=:phone, gender=:gender, birth=:birth where mid=:mid")
    @Transactional
    @Modifying
    public void update2(@Param("email") String email, @Param("phone") String phone, @Param("gender") String gender, @Param("birth") Integer birth, @Param("mid") Long mid);

    //비밀번호 변경
    @Query("update MemberEntity set mpasswd=:mpasswd where mid=:mid")
    @Transactional
    @Modifying
    public void pwUpdate(@Param("mpasswd") String mpasswd, @Param("mid") Long mid);

}
