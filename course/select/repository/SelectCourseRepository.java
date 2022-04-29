package jejufriends.course.select.repository;

import java.util.List;

import jejufriends.course.domain.Course;
import jejufriends.course.domain.CourseActivity;
import jejufriends.course.domain.CourseFood;
import jejufriends.course.domain.CourseHotel;
import jejufriends.course.domain.CourseLandMark;
import jejufriends.course.domain.WishList;
import jejufriends.course.select.domain.SelectCourseDivision;
import jejufriends.course.select.domain.SelectCourseSearch;
import soo.md.domain.Activity;
import soo.md.domain.Food;
import soo.md.domain.Hotel;
import soo.md.domain.Landmark;

public interface SelectCourseRepository {
	List<Course> selectCourseAll();
	List<Course> selectDivision(SelectCourseDivision selectCourseDivision);
	List<Course> selectSearch(SelectCourseSearch selectCourseSearch);
	
	// 코스 상세정보 가져오기
	Course selectContent(long cnum);
	
	// 코스에 포함된 컨텐츠 가져오기
	List<CourseHotel> selectCourseHotel(long cnum);
	List<CourseActivity> selectCourseActivity(long cnum);
	List<CourseFood> selectCourseFood(long cnum);
	List<CourseLandMark> selectCourseLandMark(long cnum);
	
	// 컨텐츠 상세정보 가져오기
	Hotel selectHotel(String hname);
	Activity selectActivity(String aname);
	Food selectFood(String fname);
	Landmark selectLandMark(String lname);
	
	// 조회수 셋팅
	Long selectViews(long cnum);
	void updateViews(Course course);
	
	// 나의 일정 핸들링
	void insertWishList(WishList wishList);
	WishList selectWishList(WishList wishList);
}
