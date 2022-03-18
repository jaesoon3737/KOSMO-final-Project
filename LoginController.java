package members.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.domain.LoginMember;
import members.member.domain.Member;
import members.member.domain.SessionLoginConst;
import members.member.domain.SessionMember;
import members.member.service.LoginService;
import members.member.utils.MessageUtils;
import members.member.utils.UserSha256;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("jeju/member/login")
public class LoginController {
	
	private final LoginService loginService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	@GetMapping()
	public String loginForm(Model model) {
		model.addAttribute("loginMember" , new LoginMember());
		
		return "login/login";
	}
	

	@GetMapping("/restPwd")
	public String restPwdForm() {
		return "login/restPwd";
	}

	@PostMapping()
	public String Login(@Valid @ModelAttribute LoginMember loginMember
			, BindingResult bindingResult , HttpServletRequest request , RedirectAttributes redirectAttribute ) {
		
		if(bindingResult.hasErrors()) {
			log.info(" errors !");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error : errors) {
				log.info("error = {}" , error.getDefaultMessage());
			}
			

			return "login/login";
		}
		//String encryPwd = bcryptPasswordEncoder.encode(loginMember.getPwd());
		
		//loginMember.setPwd(encryPwd);
		
		Member loginMemberCheck = loginService.loginId(loginMember);
		
		if(loginMemberCheck == null) {
			redirectAttribute.addAttribute("errorNotFound" , "1");
			return "redirect:/jeju/member/login";
		}
		
		String check = loginMemberCheck.getPwd();
		if(bcryptPasswordEncoder.matches(loginMember.getPwd(), check)){
		
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
		} else {
			    throw new BadCredentialsException( "암호가 일치하지 않습니다." );
			//return "redirect:/jeju/member/login";
		}
	}
	
	@PostMapping("/logouts")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			session.invalidate();
		}
		
		return "redirect:/index";
	}
	
	

}
