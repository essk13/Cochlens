package com.ssafy.db.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Stt {
    @Id
    @Column(name = "stt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long sttId;

    @Column(name = "stt_one_id")
    String sttOneId;

    @Column(name = "stt_recognitionStatus",nullable = false)
    Integer sttRecognitionStatus;

    @Column(name = "stt_offset",nullable = false)
    Integer sttOffset;

    @Column(name = "stt_duraton",nullable = false)
    Integer sttDuration;

    @Column(name = "stt_dispalyText",nullable = false)
    String sttDisplayText;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecture_id")
    Lecture lecture;

    @Builder
    public Stt(Long sttId, String sttOneId, Integer sttRecognitionStatus, Integer sttOffset, Integer sttDuration, String sttDisplayText, Lecture lecture){
        this.sttId = sttId;
        this.sttOneId = sttOneId;
        this.sttRecognitionStatus = sttRecognitionStatus;
        this.sttOffset = sttOffset;
        this.sttDuration = sttDuration;
        this.sttDisplayText = sttDisplayText;
    }

}