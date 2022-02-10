package com.ssafy.db.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegisterCourse {
    @Id
    @Column(name = "register_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long registerId;
    @Column(name = "is_cancel")
    boolean isCancel;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    Course course;

    @Builder
    public RegisterCourse(User user, Course course, boolean isCancel) {
        this.user = user;
        this.course = course;
        this.isCancel = isCancel;
    }
}
