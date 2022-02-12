package com.ssafy.api.dto;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Wishlist;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


public class WishlistDto {
    @Getter
    @Setter
    @ApiModel("WishlistRes")
    public static class WishlistRes {

        @ApiModelProperty(name="wishlistId", example="1")
        Long wishlistId;
        @ApiModelProperty(name="user", example="user object")
        User user;
        @ApiModelProperty(name="course", example="course object")
        Course course;

        public static WishlistDto.WishlistRes of(Wishlist wishlist) {
            WishlistDto.WishlistRes res = new WishlistDto.WishlistRes();

            res.setWishlistId(wishlist.getWishlistId());
            res.setUser(wishlist.getUser());
            res.setCourse(wishlist.getCourse());
            return res;
        }
    }
}

