package com.khit.library.repository;

import com.khit.library.dto.BookDTO;
import com.khit.library.entity.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.bname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Book> findByBnameContainingIgnoreCaseOrAuthorContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    
    //페이징 처리
    Page<Book> findAll(Pageable pageable);

}

