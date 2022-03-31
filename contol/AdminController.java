package jejufriends.member.contol;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jejufriends.member.domain.AdminUserInfo;
import jejufriends.member.domain.Paging;
import jejufriends.member.domain.PagingKeyword;
import jejufriends.member.domain.TabooWord;
import jejufriends.member.service.MemberManagementService;
import jejufriends.member.service.TabooWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/jejufriends/admin")
public class AdminController {
	
	private final TabooWordService tabooWordService;
	private final MemberManagementService memberManagementService;
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN"})
	@GetMapping("/memberManagement")
	public String memberManagementForm(Paging paging , Model model , 
			@RequestParam(value="nowPage", required=false)String nowPage , 
			@RequestParam(value="cntPerPage", required=false)String cntPerPage, 
			@RequestParam(value="catgo" , required = false)String catgo , 
			@RequestParam(value="keyword",  required = false )String keyword
			
			) {
		PagingKeyword pagingKeyword = new PagingKeyword(catgo, keyword);
		int total = memberManagementService.countMember(pagingKeyword);
		
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "5";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) { 
			cntPerPage = "5";
		}
		if (catgo == null && keyword == null) {
			catgo = "idNumber";
			keyword = "";
		} else if (catgo == null) {
			catgo = "idNumber";
		} else if (keyword == null) {
			keyword = "";
		}
		
		paging = new Paging(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage) , catgo , keyword);
		
		model.addAttribute("paging", paging);
		List<AdminUserInfo> memberList =memberManagementService.findAll(paging);
		model.addAttribute("memberList" , memberList);
		return "admin/adminForm";
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN"})
	@GetMapping("/taboolist")
	public ModelAndView tabooListForm() {
		List<TabooWord> taboolist = tabooWordService.findAll();
		ModelAndView mv = new ModelAndView("admin/taboolist" , "tabooAll" , taboolist);
		return mv;
	}
}
