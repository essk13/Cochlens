package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegister_course is a Querydsl query type for Register_course
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRegister_course extends EntityPathBase<Register_course> {

    private static final long serialVersionUID = -1372239563L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegister_course register_course = new QRegister_course("register_course");

    public final QCourse course;

    public final BooleanPath isCancel = createBoolean("isCancel");

    public final NumberPath<Long> registerId = createNumber("registerId", Long.class);

    public final QUser user;

    public QRegister_course(String variable) {
        this(Register_course.class, forVariable(variable), INITS);
    }

    public QRegister_course(Path<? extends Register_course> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegister_course(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegister_course(PathMetadata metadata, PathInits inits) {
        this(Register_course.class, metadata, inits);
    }

    public QRegister_course(Class<? extends Register_course> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.course = inits.isInitialized("course") ? new QCourse(forProperty("course"), inits.get("course")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

