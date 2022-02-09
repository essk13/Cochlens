package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLecture is a Querydsl query type for Lecture
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLecture extends EntityPathBase<Lecture> {

    private static final long serialVersionUID = -501107844L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLecture lecture = new QLecture("lecture");

    public final QCourse course;

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
        this(Lecture.class, forVariable(variable), INITS);
    }

    public QLecture(Path<? extends Lecture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLecture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLecture(PathMetadata metadata, PathInits inits) {
        this(Lecture.class, metadata, inits);
    }

    public QLecture(Class<? extends Lecture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.course = inits.isInitialized("course") ? new QCourse(forProperty("course"), inits.get("course")) : null;
    }

}

