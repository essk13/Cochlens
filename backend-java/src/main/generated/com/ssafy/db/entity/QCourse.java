package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourse is a Querydsl query type for Course
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCourse extends EntityPathBase<Course> {

    private static final long serialVersionUID = 1259962973L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCourse course = new QCourse("course");

    public final StringPath courseCategory = createString("courseCategory");

    public final DatePath<java.util.Date> courseCloseDate = createDate("courseCloseDate", java.util.Date.class);

    public final NumberPath<Integer> courseCycle = createNumber("courseCycle", Integer.class);

    public final StringPath courseDescription = createString("courseDescription");

    public final NumberPath<Integer> courseFee = createNumber("courseFee", Integer.class);

    public final NumberPath<Long> courseId = createNumber("courseId", Long.class);

    public final StringPath courseIntroVideo = createString("courseIntroVideo");

    public final NumberPath<Integer> courseLimitPeople = createNumber("courseLimitPeople", Integer.class);

    public final StringPath courseName = createString("courseName");

    public final DatePath<java.util.Date> courseOpenDate = createDate("courseOpenDate", java.util.Date.class);

    public final StringPath courseThumbnail = createString("courseThumbnail");

    public final QUser user;

    public QCourse(String variable) {
        this(Course.class, forVariable(variable), INITS);
    }

    public QCourse(Path<? extends Course> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCourse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCourse(PathMetadata metadata, PathInits inits) {
        this(Course.class, metadata, inits);
    }

    public QCourse(Class<? extends Course> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

