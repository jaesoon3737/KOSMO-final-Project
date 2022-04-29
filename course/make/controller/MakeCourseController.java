package jejufriends.course.make.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jejufriends.course.domain.Course;
import jejufriends.course.make.domain.MakeCourse;
import jejufriends.course.make.domain.SaveCourse;
import jejufriends.course.make.domain.SaveCourseContent;
import jejufriends.course.make.service.MakeCourseService;
import jejufriends.member.domain.Member;
import lombok.RequiredArgsConstructor;
import soo.md.domain.Food;

@Controller
@RequestMapping("/jejufriends/make_course")
@RequiredArgsConstructor
public class MakeCourseController {
	private final MakeCourseService makeCourseService;
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("make.do")
	public String make() {
		makeCourseService.setContents();  //map 셋팅
		return "/make_course/makecourse";
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("search")
	@ResponseBody
	public List<MakeCourse> search(String keyword) {
		return makeCourseService.search(keyword);
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("contentInfo.do")
	public ModelAndView contentInfo(String contentname) {
		ModelAndView modelAndView = new ModelAndView();
		if(contentname != null) {
			if(contentname.trim().length() != 0) {
				String contentType = makeCourseService.findContentType(contentname);
				if(contentType != null) {
					if(contentType.equals("COURSE_FOOD")) {
						Food food = makeCourseService.contentFoodInfo(contentname);
						modelAndView.setViewName("/make_course/make_content_food");
						modelAndView.addObject("food", food);
						return modelAndView;
					}else {
						MakeCourse makeCourse = makeCourseService.contentInfo(contentname);
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
	@PostMapping("findcname")
	@ResponseBody
	public boolean findCname(String cname) {
		return makeCourseService.findCourseName(cname);
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("saveCourse")
	@ResponseBody
	public boolean saveCourse(@RequestBody SaveCourse savecourse) {
		boolean flag = false;
		if(savecourse != null) {
			flag = makeCourseService.saveCourse(savecourse);
		}
		return flag;
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@RequestMapping("findNick")
	@ResponseBody
	public Member findNick(String email) {
		Member member = new Member();
		String nickName = makeCourseService.findNick(email);
		member.setNickName(nickName);
		return member;
	}
}
