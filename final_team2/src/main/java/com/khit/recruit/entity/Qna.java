package com.khit.recruit.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "q_board")
@Setter
@Getter
@Entity
public class Qna extends BaseEntity {

    @Id
    @Column(name = "qna_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String content;

    @Column(columnDefinition = "Integer default 0")
    private Integer likes;

    @Column(columnDefinition = "Integer default 0")
    private Integer views;


    public void increaseViews(Qna qna) {
        this.views = this.views + 1;
    }

    public void increaseLike() {
        this.likes = this.likes + 1;
    }
}
