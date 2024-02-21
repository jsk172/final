package com.khit.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khit.library.dto.RentalReturnDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "rentalreturn")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RentalReturn {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rentalId; // 대출번호

	@Column(updatable = false)
	@CreationTimestamp
	private Timestamp rentalDate; //대출일

	@Column
	private Timestamp returnDate; //반납일

	@Column
	private Timestamp deadlineDate; //반납마감일

	public static RentalReturn toSaveEntity(RentalReturnDTO rentalReturnDTO){
		//반납일은 대출일 + 7일로 설정
		RentalReturn rentalReturn = RentalReturn.builder()
				.rentalDate(rentalReturnDTO.getRentalDate())
				.returnDate(rentalReturnDTO.getReturnDate())
				.deadlineDate(new Timestamp(rentalReturnDTO.getRentalDate().getTime() + 7*24*60*60*1000))
				.member(rentalReturnDTO.getMember())
				.book(rentalReturnDTO.getBook())
				.build();
		return rentalReturn;
	}
	public static RentalReturn toUpdateEntity(RentalReturnDTO rentalReturnDTO){
		RentalReturn rentalReturn = RentalReturn.builder()
				.rentalId(rentalReturnDTO.getRentalId())
				.rentalDate(rentalReturnDTO.getRentalDate())
				.returnDate(rentalReturnDTO.getReturnDate())
				.deadlineDate(rentalReturnDTO.getDeadlineDate())
				.member(rentalReturnDTO.getMember())
				.book(rentalReturnDTO.getBook())
				.build();
		return rentalReturn;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	private Member member;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	private Book book;
}
