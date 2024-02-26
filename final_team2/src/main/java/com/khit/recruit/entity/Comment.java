package com.khit.recruit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@Setter
@Getter
@Entity
public class Comment extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String content;

	@Column
	private String author;

	@Column
	private String avatarURL;

	@JsonIgnore
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "free_id")
	private Free free;

	@JsonIgnore
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "noti_id")
	private Noti noti;

	@JsonIgnore
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "qna_id")
	private Qna qna;

	@JsonIgnore
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "review_id")
	private Review review;

	
}
