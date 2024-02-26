package com.khit.recruit.dto;

import jakarta.persistence.Column;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReviewDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
    private String filename;
    private String filepath;
    private Integer likes;
    private Integer views;
    private Integer rating;
    private Timestamp createdDate;
    private Long commentCount;


}
