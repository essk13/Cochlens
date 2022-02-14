package com.ssafy.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reviewId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "review_date", nullable = false)
    Date reviewDate;
    @Column(name = "review_content", nullable = false)
    String reviewContent;
    @Column(name = "review_grade", nullable = false)
    int reviewGrade;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    Course course;

    @Builder
    public Review(Long reviewId, Date reviewDate, String reviewContent, int reviewGrade, User user, Course course){

        this.reviewId = reviewId;
        this.reviewDate = reviewDate;
        this.reviewContent = reviewContent;
        this.reviewGrade = reviewGrade;
        this.user = user;
        this.course = course;
    }
}
