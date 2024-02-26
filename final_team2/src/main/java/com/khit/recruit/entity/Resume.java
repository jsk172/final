package com.khit.recruit.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Resume extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String title;

	@Column
	private String name;

	@Column
	private String birthday_year;

	@Column
	private String birthday_month;

	@Column
	private String birthday_day;

	@Column
	private String gender;

	@Column
	private String phone;

	@Column
	private String email_prev;

	@Column
	private String email_next;

	@Column
	private String zoneCode;

	@Column
	private String address;

	@Column
	private String edu_last_prev;

	@Column
	private String edu_last_next;

	@Column
	private String edu_detail_prev;

	@Column
	private String edu_detail_next;

	@Column
	private String career;

	@Column
	private String military;

	@Column
	private String license;

	@Column
	private String certificate;

	@Column
	private String growth;

	@Column
	private String specialty;

	@Column
	private String exp;

	@Column
	private String language;

	@Column
	private String filename;

	@Column
	private String filepath;
	
	@ManyToOne
	@JoinColumn
	private MemberEntity member;

}
