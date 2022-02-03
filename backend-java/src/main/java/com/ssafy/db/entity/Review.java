package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
}
