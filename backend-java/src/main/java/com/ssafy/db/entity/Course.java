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
public class Course {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long courseId;
    @Column(name = "course_name", nullable = false)
    String courseName;
    @Column(name = "course_description", nullable = false)
    String courseDescription;
    @Column(name = "course_category", nullable = false)
    String courseCategory;
    @Column(name = "course_thumbnail")
    String courseThumbnail;
    @Column(name = "course_intro_video")
    String courseIntroVideo;
    @Temporal(TemporalType.DATE)
    @Column(name = "course_open_date", nullable = false)
    Date courseOpenDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "course_close_date")
    Date courseCloseDate;
    @Column(name = "course_fee", nullable = false)
    int courseFee;
    @Column(name = "course_cycle", nullable = false)
    int courseCycle;
    @Column(name = "course_limit_people", nullable = false)
    int courseLimitPeople;

    @Column(name = "course_wish_count")
    int courseWishCount;
    @Column(name = "course_review_count")
    int courseReviewCount;
    @Column(name = "course_review_rate_average")
    double courseReviewRateAverage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    public void increaseWishCount() {
        courseWishCount++;
    }

    public void increaseReviewCount() {
        courseReviewCount++;
    }

    @Builder
    public Course(Long courseId, String courseName, String courseDescription, Date courseOpenDate, Date courseCloseDate,
                  String courseCategory, int courseCycle, String courseThumbnail, String courseIntroVideo,
                  int courseLimitPeople, int courseFee, User user){

        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCycle = courseCycle;
        this.courseThumbnail = courseThumbnail;
        this.courseIntroVideo = courseIntroVideo;
        this.courseDescription = courseDescription;
        this.courseCategory = courseCategory;
        this.courseLimitPeople = courseLimitPeople;
        this.courseFee = courseFee;
        this.courseOpenDate = courseOpenDate;
        this.courseCloseDate = courseCloseDate;
        this.user = user;
    }
}