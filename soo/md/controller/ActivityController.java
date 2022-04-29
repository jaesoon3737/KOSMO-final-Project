package soo.md.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import soo.md.domain.Activity;
import soo.md.domain.ActivityListResult;
import soo.md.domain.ActivitySearchListResult;
import soo.md.domain.ActivityreviewVo;
import soo.md.service.ActivityService;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("jejufriends/activity")
public class ActivityController {
	private ActivityService activityService;
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("list.do")
	public ModelAndView list(HttpServletRequest request, HttpSession session) {
		String cpStr = request.getParameter("cp");
		String psStr = request.getParameter("ps");
		
		//(1) cp 
		int cp = 1;
		if(cpStr == null) {
			Object cpObj = session.getAttribute("cp");
			if(cpObj != null) {
				cp = (Integer)cpObj;
			}
		}else {
			cpStr = cpStr.trim();
			cp = Integer.parseInt(cpStr);
		}
		session.setAttribute("cp", cp);
		
		//(2) ps 
		int ps = 16; // 한 페이지당 16개의 게시글
		if(psStr == null) {
			Object psObj = session.getAttribute("ps");
			if(psObj != null) {
				ps = (Integer)psObj;
			}
		}else {
			psStr = psStr.trim();
			int psParam = Integer.parseInt(psStr);
			
			Object psObj = session.getAttribute("ps");
			if(psObj != null) {
				int psSession = (Integer)psObj;
				if(psSession != psParam) {
					cp = 1;
					session.setAttribute("cp", cp);
				}
			}else {
				if(ps != psParam) {
					cp = 1;
					session.setAttribute("cp", cp);
				}
			}
			ps = psParam;
		}
		session.setAttribute("ps", ps);
		
		
		//(3) ModelAndView 
		ActivityListResult listResult = activityService.getActivityListResult(cp, ps);
		ModelAndView mv = new ModelAndView("activity/list", "listResult", listResult);
		if(listResult.getList().size() == 0) {
			if(cp > 1)
				return new ModelAndView("redirect:list.do?cp="+(cp-1));
			else 
				return new ModelAndView("activity/list", "listResult", null);
		}else {
			return mv;
		}
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("content.do")
	public String content(long anum, Model model, Principal principal) {
		// 닉네임 조회
		String email = principal.getName();
		String nick = activityService.findNick(email);
				
		Activity activity = activityService.getActivity(anum);
		List<ActivityreviewVo> activityreviewVo = activityService.reviewSelect(anum);
		model.addAttribute("activity", activity);
		model.addAttribute("activityreviewVo", activityreviewVo);
		model.addAttribute("nick", nick);
		return "activity/content";
	}
	//인서트
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("/review.do")
	public String review(ActivityreviewVo activityreviewVo ) {
		long anum = activityreviewVo.getAnum();
		activityService.review(activityreviewVo);
		return "redirect:content.do?anum="+anum;
	}
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("/search.do")
	public ModelAndView search(HttpServletRequest request, HttpSession session) {
		String cpStr = request.getParameter("cp");
		String psStr = request.getParameter("ps");
		String surf = request.getParameter("surf");
		String search_key = request.getParameter("search_key");
		int cp = 1;
		if (cpStr == null) {
			Object cpObj = session.getAttribute("cp");
			if (cpObj != null) {
				cp = (Integer) cpObj;
			}
		} else {
			cpStr = cpStr.trim();
			cp = Integer.parseInt(cpStr);
		}
		session.setAttribute("cp", cp);

		int ps = 12;
		if (psStr == null) {
			Object psObj = session.getAttribute("ps");
			if (psObj != null) {
				ps = (Integer) psObj;
			}
		} else {
			psStr = psStr.trim();
			int psParam = Integer.parseInt(psStr);

			Object psObj = session.getAttribute("ps");
			if (psObj != null) {
				int psSession = (Integer) psObj;
				if (psSession != psParam) {
					cp = 1;
					session.setAttribute("cp", cp);
				}
			} else {
				if (ps != psParam) {
					cp = 1;
					session.setAttribute("cp", cp);
				}
			}
			ps = psParam;
		}
		session.setAttribute("ps", ps);
		ActivitySearchListResult listResult = activityService.getActivitySearchListResult(cp, ps, surf, search_key);
		ModelAndView mv = new ModelAndView("activity/activitySearchList", "listResult", listResult);

		if (listResult.getList().size() == 0) {
			if (cp > 1) {
				return new ModelAndView("redirect:search.do?cp=" + (cp - 1));
			} else {
				return new ModelAndView("activity/searchList", "listResult", null);
			}
		} else {
			return mv;
		}
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("autoData.json")
	public @ResponseBody List<Activity> getActivityList(String surf, String search_key) {// subject는 검색창에 입력한 텍스트, search_key는
																				// '제목'인지 '내용'인지.
		surf = surf.toLowerCase();
		List<Activity> list;
		if ("subject".equals(search_key)) {
			list = activityService.selectBySubject(surf);
		} else {
			list = activityService.selectByContent(surf);
		}
		return list;
	}
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("/del.do")
	public String delete(long arnum, long anum) {
		activityService.remove(arnum);
		return "redirect:content.do?anum="+anum;
	}
	
}
