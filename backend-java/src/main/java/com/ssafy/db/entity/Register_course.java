package com.ssafy.db.entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Register_course {
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
}
