package com.ssafy.api.service;

import com.google.common.primitives.Ints;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.CourseDto;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RegisterCourseRepository registerCourseRepository;

    @Autowired
    RegisterCourseRepositorySupport registerCourseRepositorySupport;

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QRegisterCourse qRegisterCourse = QRegisterCourse.registerCourse;
    QWishlist qWishlist = QWishlist.wishlist;

    @Override
    public Course createCourse(User user, CourseDto.CourseInsertReq courseInsertInfo) {


        Course course = Course.builder()
                .courseName(courseInsertInfo.getCourseName())
                .courseDescription(courseInsertInfo.getCourseDescription())
                .courseOpenDate(courseInsertInfo.getCourseOpenDate())
                .courseCloseDate(courseInsertInfo.getCourseCloseDate())
                .courseCycle(courseInsertInfo.getCourseCycle())
                .courseThumbnail(courseInsertInfo.getCourseThumbnail())
                .courseCategory(courseInsertInfo.getCourseCategory())
                .courseLimitPeople(courseInsertInfo.getCourseLimitPeople())
                .courseFee(courseInsertInfo.getCourseFee())
                .courseIntroVideo(courseInsertInfo.getCourseIntroVideo())
                .user(user)
                .build();

        return courseRepository.save(course);

    }

    @Override
    public List<CourseDto.CourseListRes> getCourseList() {
        List<CourseDto.CourseListRes> result = new ArrayList<>();
        List<Course> list = courseRepository.findAll();

        for (Course course : list) {
            CourseDto.CourseListRes courseRes = new CourseDto.CourseListRes();

            courseRes.setCourseId(course.getCourseId());
            courseRes.setCourseName(course.getCourseName());
            courseRes.setCourseDescription(course.getCourseDescription());
            courseRes.setCourseThumbnail(course.getCourseThumbnail());
            courseRes.setCourseCategory(course.getCourseCategory());
            courseRes.setInstructorName(course.getUser().getUserName());

            result.add(courseRes);
        }
        return result;
    }

    @Override
    public Course getCourseByCourseId(Long courseId) {
        return courseRepository.getOne(courseId);
    }



    @Override
    public Course updateCourse(Long courseId, CourseDto.CourseInsertReq courseInsertInfo){
        Course course = courseRepository.getOne(courseId);
        Course newCourse = Course.builder()
                .courseId(course.getCourseId())
                .courseName(courseInsertInfo.getCourseName())
                .courseDescription(courseInsertInfo.getCourseDescription())
                .courseOpenDate(courseInsertInfo.getCourseOpenDate())
                .courseCloseDate(courseInsertInfo.getCourseCloseDate())
                .courseCycle(courseInsertInfo.getCourseCycle())
                .courseThumbnail(courseInsertInfo.getCourseThumbnail())
                .courseCategory(courseInsertInfo.getCourseCategory())
                .courseLimitPeople(courseInsertInfo.getCourseLimitPeople())
                .courseFee(courseInsertInfo.getCourseFee())
                .courseIntroVideo(courseInsertInfo.getCourseIntroVideo())
                .user(course.getUser())
                .build();


        return courseRepository.save(newCourse);
    }

    @Override
    public void registerCourse(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);
        RegisterCourse registerCourse = RegisterCourse.builder()
                .user(user)
                .course(course)
                .build();
        registerCourseRepository.save(registerCourse);

        //이 강좌 수강인원
//        List<Map<String, Object>> result = new ArrayList<>();
//        registerCourseRepository.findAll().forEach(registerCourseList -> {
//            Map<String, Object> obj = new HashMap<>();
//            Course courseCheck = registerCourseList.getCourse();
//            if (courseCheck == course){
//                obj.put("courseId", registerCourseList.getRegisterId());
//                result.add(obj);
//            }
//        });
//        Integer joinCount = result.size();
    }

    @Override
    public void deregisterCourse(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);

//        JPA썼음
        RegisterCourse registerCourse = jpaQueryFactory.select(qRegisterCourse).from(qRegisterCourse)
        .where(qRegisterCourse.user.eq(user)).where(qRegisterCourse.course.eq(course)).fetchOne();

        registerCourseRepository.delete(registerCourse);
    }

    @Override
    public void registerWishlist(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .course(course)
                .build();

        wishlistRepository.save(wishlist);
    }

    @Override
    public void deregisterWishlist(Long userId, Long courseId) {
        User user = userRepository.getOne(userId);
        Course course = courseRepository.getOne(courseId);

//        JPA썼음
        Wishlist wishlist = jpaQueryFactory.select(qWishlist).from(qWishlist)
                .where(qWishlist.user.eq(user)).where(qWishlist.course.eq(course)).fetchOne();

        wishlistRepository.delete(wishlist);
    }

    @Override
    public List<CourseDto.CourseListRes> getBestCourseList() {

        int courseSize = courseRepository.findAll().size();

        int[] arr = new int[courseSize + 1];

        List<Wishlist> list = wishlistRepository.findAll();
        for (Wishlist wishlist : list) {
            arr[Math.toIntExact(wishlist.getCourse().getCourseId())] += 1;
        }

        List<CourseDto.CourseListRes> result = new ArrayList<>();

        int count = Math.min(arr.length, 5);

        for (int i = 0; i < count; i++) {
//            찜 개수 중 가장 큰 값
            int max = Arrays.stream(arr).max().getAsInt();

//            max 값을 가진 index 찾기(강좌 번호)
            int maxIndex = Ints.indexOf(arr, max);

            arr[maxIndex] = 0;

            if (maxIndex == 0) break;

            Course course = courseRepository.getOne(Long.valueOf(maxIndex));
            CourseDto.CourseListRes courseRes = new CourseDto.CourseListRes();

            courseRes.setCourseId(course.getCourseId());
            courseRes.setCourseName(course.getCourseName());
            courseRes.setCourseDescription(course.getCourseDescription());
            courseRes.setCourseThumbnail(course.getCourseThumbnail());
            courseRes.setCourseCategory(course.getCourseCategory());
            courseRes.setInstructorName(course.getUser().getUserName());

            result.add(courseRes);
        }
        return result;
    }
    @Override
    public List<CourseDto.CourseListRes> getRecentCourseList(Long userId){
        User user = userRepository.getOne(userId);

        List<RegisterCourse> list = registerCourseRepository.findAll();

        List<CourseDto.CourseListRes> result = new ArrayList<>();

        for (RegisterCourse registerCourseList : list) {
            if (registerCourseList.getUser() == user){

                CourseDto.CourseListRes courseRes = new CourseDto.CourseListRes();

                Course course = registerCourseList.getCourse();

                courseRes.setCourseId(course.getCourseId());
                courseRes.setCourseName(course.getCourseName());
                courseRes.setCourseDescription(course.getCourseDescription());
                courseRes.setCourseThumbnail(course.getCourseThumbnail());
                courseRes.setCourseCategory(course.getCourseCategory());
                courseRes.setInstructorName(course.getUser().getUserName());


                result.add(courseRes);
            }
        }
        return result;
    }
}
