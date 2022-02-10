package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCurriculum is a Querydsl query type for Curriculum
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCurriculum extends EntityPathBase<Curriculum> {

    private static final long serialVersionUID = 1957186941L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCurriculum curriculum = new QCurriculum("curriculum");

    public final QCourse course;

    public final NumberPath<Long> curriculumId = createNumber("curriculumId", Long.class);

    public final QLecture lecture;

    public QCurriculum(String variable) {
        this(Curriculum.class, forVariable(variable), INITS);
    }

    public QCurriculum(Path<? extends Curriculum> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCurriculum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCurriculum(PathMetadata metadata, PathInits inits) {
        this(Curriculum.class, metadata, inits);
    }

    public QCurriculum(Class<? extends Curriculum> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.course = inits.isInitialized("course") ? new QCourse(forProperty("course"), inits.get("course")) : null;
        this.lecture = inits.isInitialized("lecture") ? new QLecture(forProperty("lecture")) : null;
    }

}

