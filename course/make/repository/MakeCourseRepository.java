package jejufriends.course.make.repository;


import java.util.List;

import jejufriends.course.domain.Course;
import jejufriends.course.domain.CourseActivity;
import jejufriends.course.domain.CourseContent;
import jejufriends.course.domain.CourseFood;
import jejufriends.course.domain.CourseHotel;
import soo.md.domain.Activity;
import soo.md.domain.Food;
import soo.md.domain.Hotel;
import soo.md.domain.Landmark;

public interface MakeCourseRepository {
	// 전부 검색
	List<Hotel> selectHotelAll();
	List<Activity> selectActivityAll();
	List<Food> selectFoodAll();
	List<Landmark> selectLandmarkAll();
	
	// 좌표값 없는 컨텐츠 검색
	List<Hotel> selectHotelLocation();
	List<Activity> selectActivityLocation();
	List<Food> selectFoodLocation();
	List<Landmark> selectLandmarkLocation();
	
	// 키워드 검색
	List<Hotel> selectHotelSearch(String keyword);
	List<Activity> selectActivitySearch(String keyword);
	List<Food> selectFoodSearch(String keyword);
	List<Landmark> selectLandmarkSearch(String keyword);
	
	// 데이터베이스에 코스 저장
	void insertCourse(Course course);
	void insertCourseContent(CourseContent courseContent);
	
	// 코스 조회
	Long selectCourseNum(String cname);
	String selectCourseName(String cname);
	
	// 푸드 컨텐츠
	Food selectFood(String fname);
	
	// 닉네임 조회
	String selectNick(String email);
	// 코스 삭제 ( 필요할까? )
	
	// 코스 업데이트 ( 필요할까? )
}
