package jejufriends.member.contol;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jejufriends.member.domain.AdminUserInfo;
import jejufriends.member.domain.Member;
import jejufriends.member.domain.Paging;
import jejufriends.member.domain.PagingKeyword;
import jejufriends.member.domain.TabooContainsMember;
import jejufriends.member.domain.TabooWord;
import jejufriends.member.service.MemberManagementService;
import jejufriends.member.service.SuspendAccountService;
import jejufriends.member.service.TabooWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/jejufriends/admin")
public class AdminController {
	
	private final SuspendAccountService suspendAccountService; 
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
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN"})
	@GetMapping(value = "/deletetaboo" , produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String deletetaboo(@RequestParam Integer tabooWordNumber ){
			//Integer tabooNumber = tabooword.getTabooWordNumber();
			if(tabooWordNumber != null) {
				int deleteSuccessCheck = tabooWordService.deleteTabooWord(tabooWordNumber);
				if(deleteSuccessCheck == 1) {
					return tabooWordNumber.toString();
				} else {
					return "fail";
				}
			} else {
				return "fail";
			}
	}
	
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN"})
	@GetMapping(value = "/suspendAccount" , produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String suspendAccount(@RequestParam Integer idNumber , String enabled){
			enabled.trim();
			
			// 정지상태일 경우 0으로 변환 
			if(enabled.contains("정지")){
				enabled = "0";
			} else {
				enabled = "1";
			}
		
			Integer result = suspendAccountService.suspendAccount(idNumber , Integer.parseInt(enabled));
			// 1일 경우 업데이트 완료 
			return String.valueOf(result);
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN"})
	@GetMapping(value = "/cautionAccount" , produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String cautionAccount(@RequestParam Integer idNumber ){
			Integer result = suspendAccountService.cautionAccount(idNumber);
			// 1일 경우 업데이트 완료 
			if(result == 1) {
				Member member = suspendAccountService.findMember(idNumber);
				int cautionCount = member.getCautionCount();
				int enabled = member.getEnabled();
				if(cautionCount >= 3 && enabled == 1 ) {
					suspendAccount(idNumber , "활성");
				} 
			}
			return String.valueOf(result);
	}
	
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN"})
	@GetMapping("/tabooContainsMemberList")
	public ModelAndView tabooContainsMemberList() {
		List<TabooContainsMember> memberList = tabooWordService.tabooContainsMemberList();
		ModelAndView mv = new ModelAndView("admin/tabooContainsMemberList" , "memberList" , memberList);
		return mv;
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN"})
	@PostMapping(value = "/insertTaboo" , produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String insertTaboo(@RequestBody TabooWord tabooword){
		String insertTabooWord = tabooword.getTabooWordCheck();
		
		// 1 -> 존재함 , 0 -> 존재하지 않음 
		int existCheckNumber = tabooWordService.insertTaboo(tabooword);
		if (existCheckNumber == 0) {
			return insertTabooWord;
		} else {
			return "fail";
		}
	}
}
