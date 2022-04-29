package jejufriends.course.select.service;

import java.util.List;

import jejufriends.course.domain.Course;
import jejufriends.course.domain.WishList;
import jejufriends.course.make.domain.MakeCourse;
import jejufriends.course.select.domain.SelectCourseContent;
import jejufriends.course.select.domain.SelectCourseDivision;
import jejufriends.course.select.domain.SelectCourseSearch;
import soo.md.domain.Food;

public interface SelectCourseService {
	List<Course> courseListAll();
	List<Course> courseDivisionList(SelectCourseDivision selectCourseDivision);
	List<Course> courseSearch(SelectCourseSearch selectCourseSearch);
	
	// 코스 상세정보
	Course courseInfo(long cnum);
	
	// 컨텐츠 상세정보
	List<SelectCourseContent> courseContentInfo(long cnum);
	
	// 코스 타입 가져오기
	String findContentType(String contentname);
	
	// 컨텐츠의 상세정보를 찾아오는 메소드
	MakeCourse contentInfo(String contentname);
	
	// 푸드 컨텐츠의 상세 정보를 찾아오는 메소드
	Food contentFoodInfo(String contentname);
	
	// 조회수 셋팅
	Long checkViews(long cnum);
	void updateViews(Course course);
	
	// 나의 일정 핸들링
	void addWishList(WishList wishList);
	WishList findWishList(WishList wishList);
}
