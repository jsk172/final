package com.khit.recruit.entity;

import com.khit.recruit.dto.ReviewDTO;
import jakarta.persistence.*;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "r_board")
@Setter
@Getter
@Entity
public class Review extends BaseEntity {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String content;

    @Column
    private String filename;

    @Column
    private String filepath;

    @Column(columnDefinition = "Integer default 0")
    private Integer likes;

    @Column(columnDefinition = "Integer default 0")
    private Integer views;

    @Column(columnDefinition = "Integer default 0")
    private Integer rating;


    public ReviewDTO convertToDTO() {
        return ReviewDTO.builder().id(id)
            .title(title)
            .author(author)
            .content(content)
            .filename(filename)
            .filepath(filepath)
            .likes(likes)
            .views(views)
            .rating(rating)
            .createdDate(getCreatedDate())
            .commentCount(0L).build();
    }

    public void increaseViews() {

        this.views = this.views + 1;
    }

    public void increaseLike() {
        this.likes = this.likes + 1;
    }
}
