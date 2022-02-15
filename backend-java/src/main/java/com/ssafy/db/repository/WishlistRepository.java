package com.ssafy.db.repository;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findWishlistByUserAndCourse(User user, Course course);
}

