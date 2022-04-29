package jejufriends.course.make.service;

import java.util.List;

import jejufriends.course.make.domain.MakeCourse;
import jejufriends.course.make.domain.SaveCourse;
import soo.md.domain.Food;

public interface MakeCourseService {
	// 검색을 위한 메소드
	List<MakeCourse> search(String keyword);
	// 검색을 효율적으로 하기 위한 Map 셋팅하는 메소드
	void setContents();
	// 컨텐츠의 테이블을 찾아오는 메소드
	String findContentType(String contentname);
	// 컨텐츠의 상세정보를 찾아오는 메소드
	MakeCourse contentInfo(String contentname);
	// 푸드 컨텐츠의 상세 정보를 찾아오는 메소드
	Food contentFoodInfo(String contentname);
	// 코스테이블에 저장하기 위한 메소드
	boolean saveCourse(SaveCourse saveCourse);
	// 이름 중복을 확인하기 위한 메소드
	boolean findCourseName(String cname);
	// 유저의 닉네임을 가져옴
	String findNick(String email);
}
