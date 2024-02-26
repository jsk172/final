package com.khit.recruit.repository;

import com.khit.recruit.entity.Comment;
import com.khit.recruit.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{

Page<Comment> findByFree_Id(Long id, Pageable pageable);

Page<Comment> findByQna_Id(Long id, Pageable pageable);

Page<Comment> findByNoti_Id(Long id, Pageable pageable);

Page<Comment> findByReview_Id(Long id, Pageable pageable);

}
