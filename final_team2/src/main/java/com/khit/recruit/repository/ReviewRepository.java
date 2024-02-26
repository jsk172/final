package com.khit.recruit.repository;

import com.khit.recruit.entity.Noti;
import com.khit.recruit.entity.Qna;
import com.khit.recruit.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>{

//	Page<Job> findByTitleContaining(String keyword, Pageable pageable);
//
//	@Query("SELECT j FROM Job j JOIN j.company c WHERE c.cname LIKE %:cname%")
//    Page<Job> findByCnameContaining(@Param("cname") String keyword, Pageable pageable);
//
//	Page<Job> findByAddress01Containing(String city, Pageable pageable);
//
//	Optional<Job> findByJid(Long jid);

//	Page<Noti> findAllByType(String type, Pageable pageable);
Page<Review> findByTitleContaining(String keyword, Pageable pageable);


}
