package com.khit.library.repository;

import com.khit.library.entity.RentalReturn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalReturnRepository extends JpaRepository<RentalReturn, Long> {
    //Optional<RentalReturn> findByRentalId(Long rentalId);
    RentalReturn findByRentalId(Long rentalId);

    List<RentalReturn> findByMemberMid(String mid);

    @Query("select count(*) from RentalReturn where member.memberId = :memberId and returnDate is null")
    public int rentalCount(Long memberId);

    @Query("select r1.member.memberId from RentalReturn as r1 " +
            "where(select count(*) from RentalReturn as r2 " +
            "where r2.member.memberId = r1.member.memberId " +
            "and r2.returnDate is null) >= 5 " +
            "or (r1.deadlineDate < current_date() and r1.returnDate is null) " +
            "group by r1.member.memberId")
    public List<Integer> rentalAble();

    
    //페이징 처리를 위한 메서드
  	Page<RentalReturn> findAll(Pageable pageable);


    @Query("SELECT r.book.bookId, COUNT(r.rentalId) AS rentalCount " +
            "FROM RentalReturn r " +
            "GROUP BY r.book.bookId " +
            "ORDER BY rentalCount DESC")
    List<Object[]> findOrderByRentalCount();
}

