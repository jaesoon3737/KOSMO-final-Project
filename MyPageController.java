package members.member.controller;


import java.security.Principal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.domain.Member;
import members.member.service.MemberPageService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("jeju/member/mypage")
public class MyPageController {
	
	private final MemberPageService memberPageService;
	@GetMapping
	public String myPageForm(Principal principal) {
		return "myPage/mypage";
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
		
		return "myPage/myPageInfo";
	}
}
