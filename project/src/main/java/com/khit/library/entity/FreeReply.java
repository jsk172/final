package com.khit.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "fb_reply")
@Entity
public class FreeReply extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long frid;

	@Column(length = 200, nullable = false)
	private String frcontent;

	// 회원 한명이 여러 개의 댓글 작성 가능
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Member member;

	// 게시글 하나에 여러 개의 댓글 작성 가능
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private FreeBoard freeboard;


}
