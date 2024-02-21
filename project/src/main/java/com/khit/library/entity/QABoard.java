package com.khit.library.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="qaboard")
public class QABoard extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //QA게시판 아이디
	
	@Column(nullable = false)
	private String title; //자유게시판 제목
	
	@Column(length = 2000, nullable = false)
	private String content; //자유게시판 내용
	
	@Column(nullable = true)
	private Integer hit;  //조회수
}
