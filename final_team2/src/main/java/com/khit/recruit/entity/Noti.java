package com.khit.recruit.entity;

import com.khit.recruit.dto.JobDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Currency;

@Builder
@ToString
@Table(name = "n_board")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Noti extends BaseEntity {

    @Id
    @Column(name = "noti_id")
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


    public void increaseViews() {
        this.views = this.views + 1;
    }

    public void increaseLike() {
        this.likes = this.likes + 1;
    }
}
