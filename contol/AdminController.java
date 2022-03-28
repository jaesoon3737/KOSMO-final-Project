package jejufriends.member.contol;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jejufriends.member.service.MemberPageService;
import jejufriends.member.service.TabooWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/jejufriends/admin/memberManagement")
public class AdminController {
	
	
	@GetMapping
	public String memberManagementForm() {
		return "admin/adminForm";
	}
}
