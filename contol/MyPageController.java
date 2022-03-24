package jejufriends.member.contol;

import java.security.Principal;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jejufriends.member.domain.Member;
import jejufriends.member.service.MemberPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Secured("ROLE_USER")
@RequiredArgsConstructor
@RequestMapping("jejufriends/member/mypage")
public class MyPageController {
	
	
	private final MemberPageService memberPageService;
	
	@GetMapping
	public String mypageForm(Model model) {
		return "mypage/mypage";
	}
	

	@GetMapping("memberinfo")
	public String myPageMemberInfoForm(Principal principal , Model model ) {
		String email = principal.getName();
		log.info("username = {}" , email);
		
		//User 찾아오기
		Member member = memberPageService.userInfoSelect(email);
		String emailChangeStar = memberPageService.emailChangeStar(email);
		
		log.info("emailChangeStar = {}" , emailChangeStar);
		
		String role = memberPageService.userInfoRole(email);
		String phoneNumber = member.getPhoneNumber();
		
		String ChangePhoneNumber = memberPageService.phoneNumberChangeStar(phoneNumber);
		
		log.info("ChangePhoneNumber = {}" , ChangePhoneNumber);
		
		model.addAttribute("ChangePhoneNumber" , ChangePhoneNumber);
		model.addAttribute("emailChangeStar" , emailChangeStar);
		model.addAttribute("member" , member);
		model.addAttribute("userRole", role);
		
		return "mypage/mypagememberinfo";
	}
}
