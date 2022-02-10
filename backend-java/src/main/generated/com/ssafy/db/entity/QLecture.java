package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLecture is a Querydsl query type for Lecture
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLecture extends EntityPathBase<Lecture> {

    private static final long serialVersionUID = -501107844L;

    public static final QLecture lecture = new QLecture("lecture");

    public final DatePath<java.util.Date> lectureCloseTime = createDate("lectureCloseTime", java.util.Date.class);

    public final DatePath<java.util.Date> lectureDate = createDate("lectureDate", java.util.Date.class);

    public final NumberPath<Long> lectureId = createNumber("lectureId", Long.class);

    public final StringPath lectureName = createString("lectureName");

    public final DatePath<java.util.Date> lectureOpenTime = createDate("lectureOpenTime", java.util.Date.class);

    public final DateTimePath<java.util.Date> lectureRuntime = createDateTime("lectureRuntime", java.util.Date.class);

    public final StringPath lectureState = createString("lectureState");

    public final StringPath lectureThumbnail = createString("lectureThumbnail");

    public final StringPath lectureVod = createString("lectureVod");

    public QLecture(String variable) {
        super(Lecture.class, forVariable(variable));
    }

    public QLecture(Path<? extends Lecture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLecture(PathMetadata metadata) {
        super(Lecture.class, metadata);
    }

}

