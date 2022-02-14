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
public class Lecture {
    @Id
    @Column(name = "lecture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long lectureId;
    @Column(name = "lecture_name", nullable = false)
    String lectureName;
    @Column(name = "lecture_runtime")
    Date lectureRuntime;
    @Column(name = "lecture_thumbnail")
    String lectureThumbnail;
    @Temporal(TemporalType.DATE)
    @Column(name = "lecture_date", nullable = false)
    Date lectureDate;
    @Column(name = "lecture_state", nullable = false)
    String lectureState;
    @Column(name = "lecture_vod")
    String lectureVod;
    @Temporal(TemporalType.DATE)
    @Column(name = "lecture_open_time")
    Date lectureOpenTime;
    @Temporal(TemporalType.DATE)
    @Column(name = "lecture_close_time")
    Date lectureCloseTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    Course course;

    @Builder
    public Lecture(Long lectureId, String lectureName, Date lectureRuntime, String lectureThumbnail,
                   Date lectureDate, String lectureState, String lectureVod,
                   Date lectureOpenTime, Date lectureCloseTime, Course course) {
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.lectureRuntime = lectureRuntime;
        this.lectureThumbnail = lectureThumbnail;
        this.lectureDate = lectureDate;
        this.lectureState = lectureState;
        this.lectureVod = lectureVod;
        this.lectureOpenTime = lectureOpenTime;
        this.lectureCloseTime = lectureCloseTime;
        this.course = course;
    }
}
