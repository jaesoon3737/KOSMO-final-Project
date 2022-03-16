package members.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.domain.LoginMember;
import members.member.domain.Member;
import members.member.domain.SessionLoginConst;
import members.member.domain.SessionMember;
import members.member.service.LoginService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("jeju/member/login")
public class LoginController {
	
	private final LoginService loginService;
	
	@GetMapping()
	public String loginForm() {
		return "login/login";
	}
	
	@GetMapping("/restPwd")
	public String restPwdForm() {
		return "login/restPwd";
	}
	
	@PostMapping()
	public String Login(@Valid @ModelAttribute LoginMember loginMember 
			, BindingResult bindingResult , HttpServletRequest request) {
		
		if(bindingResult.hasErrors()) {
			return "login/login";
		}
		Member loginMemberCheck = loginService.loginId(loginMember);
		
		if(loginMemberCheck == null) {
			//보여주는 구현로직해둘것 
			return "login/login";
		}
		
		log.info("잇힝 = {}" , loginMember);
		
		HttpSession session = request.getSession();
		SessionMember sessionMember = 
				new SessionMember(loginMemberCheck.getIdNumber(),loginMemberCheck.getEmail(),loginMemberCheck.getNickName());
		session.setAttribute(SessionLoginConst.LOGIN_MEMBER, sessionMember);
		session.setAttribute(SessionLoginConst.LOGIN_MEMBER_IDNUMBER, sessionMember.getIdNumber());
		session.setAttribute(SessionLoginConst.LOGIN_MEMBER_EMAIL, sessionMember.getEmail());
		session.setAttribute(SessionLoginConst.LOGIN_MEMBER_NICKNAME, sessionMember.getNickName());
		
		log.info("session = {}" , session.getAttribute(SessionLoginConst.LOGIN_MEMBER));
		log.info("session = {}" , session.getAttribute(SessionLoginConst.LOGIN_MEMBER_EMAIL));
		log.info("session = {}" , session.getAttribute(SessionLoginConst.LOGIN_MEMBER_NICKNAME));
		return "index";
	}
	
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			session.invalidate();
		}
		
		return "redirect:/index";
	}
}
