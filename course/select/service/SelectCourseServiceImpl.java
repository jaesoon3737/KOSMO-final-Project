package jejufriends.course.select.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jejufriends.course.domain.Course;
import jejufriends.course.domain.CourseActivity;
import jejufriends.course.domain.CourseFood;
import jejufriends.course.domain.CourseHotel;
import jejufriends.course.domain.CourseLandMark;
import jejufriends.course.domain.WishList;
import jejufriends.course.make.domain.MakeCourse;
import jejufriends.course.select.domain.SelectCourseContent;
import jejufriends.course.select.domain.SelectCourseDivision;
import jejufriends.course.select.domain.SelectCourseSearch;
import jejufriends.course.select.repository.SelectCourseRepository;
import soo.md.domain.Activity;
import soo.md.domain.Food;
import soo.md.domain.Hotel;
import soo.md.domain.Landmark;

@Service
public class SelectCourseServiceImpl implements SelectCourseService {
	private final SelectCourseRepository selectCourseRepository;
	private static Map<String, MakeCourse> selectContents;
	
	@Autowired
	public SelectCourseServiceImpl(SelectCourseRepository selectCourseRepository) {
		this.selectCourseRepository = selectCourseRepository;
	}
	
	@Override
	public List<Course> courseListAll() {
		return selectCourseRepository.selectCourseAll();
	}

	@Override
	public List<Course> courseDivisionList(SelectCourseDivision selectCourseDivision) {
		if(selectCourseDivision.getDivision() == 0) {
			return selectCourseRepository.selectCourseAll();
		}else {
			return selectCourseRepository.selectDivision(selectCourseDivision);
		}
	}

	@Override
	public List<Course> courseSearch(SelectCourseSearch selectCourseSearch) {
		return selectCourseRepository.selectSearch(selectCourseSearch);
	}

	@Override
	public Course courseInfo(long cnum) {
		return selectCourseRepository.selectContent(cnum);
	}
	
	@Override
	public String findContentType(String contentname) {
		if(selectContents != null) {
			return selectContents.get(contentname).getContenttype();
		}
		return null;
	}
	
	@Override
	public MakeCourse contentInfo(String contentname) {
		if(selectContents != null) {
			MakeCourse makeCourse = selectContents.get(contentname);
			return makeCourse;
		}
		return null;
	}
	
	@Override
	public Food contentFoodInfo(String contentname) {
		return selectCourseRepository.selectFood(contentname);
	}

	@Override
	public List<SelectCourseContent> courseContentInfo(long cnum) {
		List<SelectCourseContent> selectCourseContentList = new ArrayList<>();
		// 상세 컨텐츠를 위한 Map
		Map<String, MakeCourse> makeCourseMap = new ConcurrentHashMap<>();
		// 호텔 정보를 리스트에 넣는다.
		List<CourseHotel> hotelList = selectCourseRepository.selectCourseHotel(cnum);
		for(CourseHotel courseHotel: hotelList) {
			int cday = courseHotel.getCday();
			int corder = courseHotel.getOrder();
			String hname = courseHotel.getHname();
			String contentType = "HOTEL";
			Hotel hotel = selectCourseRepository.selectHotel(hname);
			
			// 코스 상세를 위한 VO
			SelectCourseContent selectCourseContent = new SelectCourseContent(
						cday, corder, hname, hotel.getHintro(), hotel.getHaddress(),
						hotel.getHopcl(), hotel.getHbreak(),
						hotel.getHphoto(), hotel.getXlocation(), hotel.getYlocation(), contentType
					);
			
			// map에 컨텐츠 채우기
			makeCourseMap.put(hotel.getHname(),
					new MakeCourse(
						hotel.getHnum(), hotel.getHname(), hotel.getDivision(),
						hotel.getStar(), hotel.getHcost(), hotel.getChoosed(),
						hotel.getHphoto(), hotel.getHaddress(), hotel.getXlocation(),
						hotel.getYlocation(), "HOTEL", hotel.getHintro(),
						hotel.getHintro2(), hotel.getHphone(), hotel.getHopcl(),
						hotel.getHbreak(), hotel.getHphoto2(),
						hotel.getHphoto3(), hotel.getViews(), null, null, null
					));
			
			selectCourseContentList.add(selectCourseContent);
		}
		// 엑티비티 정보를 리스트에 넣는다.
		List<CourseActivity> activityList = selectCourseRepository.selectCourseActivity(cnum);
		for(CourseActivity courseActivity: activityList) {
			int cday = courseActivity.getCday();
			int corder = courseActivity.getOrder();
			String aname = courseActivity.getAname();
			String contentType = "ACTIVITY";
			Activity activity = selectCourseRepository.selectActivity(aname);
			
			// 코스 상세를 위한 VO
			SelectCourseContent selectCourseContent = new SelectCourseContent(
						cday, corder, aname, activity.getAintro(), activity.getAaddress(),
						activity.getAopcl(), activity.getAbreak(),
						activity.getAphoto(), activity.getXlocation(), activity.getYlocation(), contentType
					);
			
			// map에 컨텐츠 채우기
			makeCourseMap.put(activity.getAname(), 
					new MakeCourse(
						activity.getAnum(), activity.getAname(), activity.getDivision(),
						activity.getStar(), activity.getAcost(), activity.getChoosed(),
						activity.getAphoto(), activity.getAaddress(), activity.getXlocation(),
						activity.getYlocation(), "ACTIVITY", activity.getAintro(),
						activity.getAintro2(), activity.getAphone(), activity.getAopcl(),
						activity.getAbreak(), activity.getAphoto2(),
						activity.getAphoto3(), activity.getViews(), null, null, null
					));
			
			selectCourseContentList.add(selectCourseContent);
		}
		// 푸드 정보를 리스트에 넣는다.
		List<CourseFood> foodList = selectCourseRepository.selectCourseFood(cnum);
		for(CourseFood courseFood: foodList) {
			int cday = courseFood.getCday();
			int corder = courseFood.getOrder();
			String fname = courseFood.getFname();
			String contentType = "FOOD";
			Food food = selectCourseRepository.selectFood(fname);
			
			// 코스 상세를 위한 VO
			SelectCourseContent selectCourseContent = new SelectCourseContent(
						cday, corder, fname, food.getFintro(), food.getFaddress(),
						food.getFopcl(), food.getFbreak(), food.getFphoto(), food.getXlocation(), 
						food.getYlocation(), contentType
					);
			
			// map에 컨텐츠 채우기
			long foodCost = (food.getFcost() + food.getFcost2() + food.getFcost3()) / 3;
			makeCourseMap.put(food.getFname(),
					new MakeCourse(
						food.getFnum(), food.getFname(), food.getDivision(),
						food.getStar(), foodCost, food.getChoosed(), food.getFphoto(), 
						food.getFaddress(), food.getXlocation(), food.getYlocation(),
						"FOOD", food.getFintro(), food.getFintro2(),
						food.getFphone(), food.getFopcl(), food.getFbreak(),
						food.getFphoto2(), food.getFphoto3(),
						food.getViews(), food.getFmenu(), food.getFmenu2(),
						food.getFmenu3()
					));
			
			selectCourseContentList.add(selectCourseContent);
		}
		// 랜드마크 정보를 리스트에 넣는다.
		List<CourseLandMark> landMarkList = selectCourseRepository.selectCourseLandMark(cnum);
		for(CourseLandMark courseLandMark: landMarkList) {
			int cday = courseLandMark.getCday();
			int corder = courseLandMark.getOrder();
			String lname = courseLandMark.getLname();
			String contentType = "LANDMARK";
			Landmark landMark = selectCourseRepository.selectLandMark(lname);
			
			// 코스 상세를 위한 VO
			SelectCourseContent selectCourseContent = new SelectCourseContent(
						cday, corder, lname, landMark.getLintro(), landMark.getLaddress(),
						landMark.getLopcl(), null,
						landMark.getLphoto(), landMark.getXlocation(), landMark.getYlocation(), contentType
					);
			
			// map에 컨텐츠 채우기
			makeCourseMap.put(landMark.getLname(),
					new MakeCourse(
						landMark.getLnum(), landMark.getLname(), landMark.getDivision(),
						landMark.getStar(), landMark.getLcost(), landMark.getChoosed(),
						landMark.getLphoto(), landMark.getLaddress(), landMark.getXlocation(),
						landMark.getYlocation(), "LANDMARK", landMark.getLintro(),
						landMark.getLintro2(), landMark.getLphone(), landMark.getLopcl(),
						null, landMark.getLphoto2(),
						landMark.getLphoto3(), landMark.getViews(), null, null, null
					));
			
			selectCourseContentList.add(selectCourseContent);
		}
		
		selectContents = makeCourseMap;
		
		return selectCourseContentList;
	}

	@Override
	public Long checkViews(long cnum) {
		return selectCourseRepository.selectViews(cnum);
	}

	@Override
	public void updateViews(Course course) {
		selectCourseRepository.updateViews(course);
	}

	@Override
	public void addWishList(WishList wishList) {
		selectCourseRepository.insertWishList(wishList);
	}

	@Override
	public WishList findWishList(WishList wishList) {
		return selectCourseRepository.selectWishList(wishList);
	}

}
