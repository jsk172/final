package com.khit.library.repository;

import com.khit.library.entity.FreeBoard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    
	Page<FreeBoard> findByFbtitleContaining(String keyword, Pageable pageable);

	Page<FreeBoard> findByFbcontentContaining(String keyword, Pageable pageable);
	
    @Modifying
	@Query(value="update FreeBoard b set b.fbhit=b.fbhit+1 where b.fbid=:fbid")
	public void updateHits(Long fbid);
    
	//페이징 처리를 위한 메서드
	Page<FreeBoard> findAll(Pageable pageable);

    List<FreeBoard> findTop5ByOrderByCreatedDateDesc();
}