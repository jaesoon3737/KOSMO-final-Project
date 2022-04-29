package jejufriends.course.select.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jejufriends.course.domain.Course;
import jejufriends.course.domain.WishList;
import jejufriends.course.make.domain.MakeCourse;
import jejufriends.course.make.service.MakeCourseService;
import jejufriends.course.select.domain.SelectCourseContent;
import jejufriends.course.select.domain.SelectCourseDivision;
import jejufriends.course.select.domain.SelectCourseSearch;
import jejufriends.course.select.service.SelectCourseService;
import oracle.jdbc.proxy.annotation.Post;
import soo.md.domain.Food;

@Controller
@RequestMapping("/jejufriends/select_course")
public class SelectCourseController {
	private final SelectCourseService selectCourseService;
	
	@Autowired
	public SelectCourseController(SelectCourseService selectCourseService) {
		this.selectCourseService = selectCourseService;
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("select.do")
	public String select() {
		return "/select_course/selectcourse";
	}
	
	// ajax 
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("selectDivision")
	@ResponseBody
	public List<Course> selectAll(SelectCourseDivision selectCourseDivision) {
		return selectCourseService.courseDivisionList(selectCourseDivision);
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("selectSearch")
	@ResponseBody
	public List<Course> selectSearch(SelectCourseSearch selectCourseSearch) {
		return selectCourseService.courseSearch(selectCourseSearch);
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("courseContent.do")
	public ModelAndView selectContent(long cnum) {
		// 조회수 올리기
		Long views = selectCourseService.checkViews(cnum);
		if(views == null) {
			views = 0L;
		}
		views++;
		Course courseTemp = new Course();
		courseTemp.setCnum(cnum);
		courseTemp.setViews(views);
		selectCourseService.updateViews(courseTemp);
		
		// 코스 객체 셋팅
		Course course = selectCourseService.courseInfo(cnum);
		List<SelectCourseContent> selectCourseContentList = selectCourseService.courseContentInfo(cnum);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/select_course/select_course_content");
		modelAndView.addObject("course", course);
		modelAndView.addObject("selectCourseContentList", selectCourseContentList);
		return modelAndView;
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("selectcontentInfo.do")
	public ModelAndView contentInfo(String contentname) {
		ModelAndView modelAndView = new ModelAndView();
		if(contentname != null) {
			if(contentname.trim().length() != 0) {
				String contentType = selectCourseService.findContentType(contentname);
				if(contentType != null) {
					if(contentType.equals("FOOD")) {
						Food food = selectCourseService.contentFoodInfo(contentname);
						modelAndView.setViewName("/make_course/make_content_food");
						modelAndView.addObject("food", food);
						return modelAndView;
					}else {
						MakeCourse makeCourse = selectCourseService.contentInfo(contentname);
						if(makeCourse != null) {
							modelAndView.setViewName("/make_course/make_content");
							modelAndView.addObject("makeCourse", makeCourse);
							return modelAndView;
						}
					}
				}
			}
		}
		modelAndView.setViewName("/error/error");
		return modelAndView;
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("wishList")
	@ResponseBody
	public WishList wishList(WishList wishList) {
		WishList findWishList = selectCourseService.findWishList(wishList);
		if(findWishList == null) {
			selectCourseService.addWishList(wishList);
			return new WishList(-1L, -1L, null, null, null, true);
		}else {
			return new WishList(-1L, -1L, null, null, null, false);
		}
	}
	
}
