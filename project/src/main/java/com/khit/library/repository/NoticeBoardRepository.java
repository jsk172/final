package com.khit.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.khit.library.entity.NoticeBoard;

import java.util.List;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long>{

	//페이징 처리를 위한 메서드
	Page<NoticeBoard> findAll(Pageable pageable);

	//조회수
	@Modifying
	@Query(value="update NoticeBoard b set b.nbhit=b.nbhit+1 where b.nbid=:nbid")
	public void updateHits(Long nbid);

    List<NoticeBoard> findTop5ByOrderByCreatedDateDesc();
	
}