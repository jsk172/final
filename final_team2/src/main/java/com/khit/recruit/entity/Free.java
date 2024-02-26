package com.khit.recruit.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@ToString
@Table(name = "f_board")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Free extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_id")
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


    public void increaseViews() {
        this.views = this.views + 1;
    }

    public void increaseLike() {
        this.likes = this.likes + 1;
    }
}
