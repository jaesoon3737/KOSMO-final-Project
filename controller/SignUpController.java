package members.member.controller;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.domain.Member;
import members.member.service.SignService;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("jeju/member/signup")
public class SignUpController {
	
	
	private final SignService signService;
	
	
	@GetMapping()
	public String signUpForm(Model model) {
		model.addAttribute("member" , new Member());
		return "signUp/signUp";
	}

	@PostMapping()
	public String signUp( @ModelAttribute Member member , BindingResult bindingResult  , RedirectAttributes redirectAttribute) {
				//회원검증  필터 생성하기
				
				log.info("어어 안녕 = {}" , member);
				/*
				if(!StringUtils.hasText(member.getEmail())) {
					bindingResult.addError
						(new FieldError("member" , "email" , member.getEmail() 
								, false , null , null, "아이디를 입력하세요."));
				}
				
				if(!StringUtils.hasText(member.getPwd())) {
					bindingResult.addError
					(new FieldError("member" , "pwd" , member.getPwd() 
							, false , null , null, "비밀번호를 입력하세요."));
				}
				

				
				if(member.getBirth() == null || member.getBirth().toString().length() != 6 ) {
					bindingResult.addError
					(new FieldError("member" , "pwd" , member.getBirth() 
							, false , null , null, "생년월일을 입력하세요."));
				}
				
				if(!StringUtils.hasText(member.getName())) {
					bindingResult.addError
					(new FieldError("member" , "name" , member.getName() 
							, false , null , null, "이름을 입력하세요."));
				}
				
				if(!StringUtils.hasText(member.getNickName())) {
					bindingResult.addError
					(new FieldError("member" , "pwd" , member.getNickName() 
							, false , null , null, "닉네임을 입력하세요."));
				}
				
				if(bindingResult.hasErrors()) {
					log.info("error = {} " , bindingResult);
					return "signUp";
				}
				*/
				//성공시
				signService.addMember(member);
				
				//signServiceImpl.addMember(member);
				//redirectAttribute.addAttribute("status" , "회원가입에 성공하였습니다.");
				//redirectAttribute.addAttribute("LoginMemberid" , member.getEmail());
		return "redirect:/jeju/member/login";
	} 

	@ResponseBody
	@GetMapping("emailCheck")
	public void emailCheck(String email , HttpServletResponse response) {
		String data = signService.emailCheckSelect(email);
		//log.info("emailCheck = {}" , email);
		//log.info("data = {}" , data);
		try {
			response.setContentType("text/plain;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(data);
		} catch (IOException io) {
			
		}
		
	}
}
