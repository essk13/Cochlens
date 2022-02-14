package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ssafy.db.entity.QUser.user;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRepositorySupport{

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public Optional<User> findUserByEmail(String email) {
        User user = jpaQueryFactory.select(QUser.user).from(QUser.user)
                .where(QUser.user.email.eq(email)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }
}
