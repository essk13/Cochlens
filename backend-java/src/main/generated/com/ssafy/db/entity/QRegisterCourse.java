package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegisterCourse is a Querydsl query type for RegisterCourse
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRegisterCourse extends EntityPathBase<RegisterCourse> {

    private static final long serialVersionUID = -1250265888L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegisterCourse registerCourse = new QRegisterCourse("registerCourse");

    public final QCourse course;

    public final BooleanPath isCancel = createBoolean("isCancel");

    public final NumberPath<Long> registerId = createNumber("registerId", Long.class);

    public final QUser user;

    public QRegisterCourse(String variable) {
        this(RegisterCourse.class, forVariable(variable), INITS);
    }

    public QRegisterCourse(Path<? extends RegisterCourse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegisterCourse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegisterCourse(PathMetadata metadata, PathInits inits) {
        this(RegisterCourse.class, metadata, inits);
    }

    public QRegisterCourse(Class<? extends RegisterCourse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.course = inits.isInitialized("course") ? new QCourse(forProperty("course"), inits.get("course")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

