package members.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
import members.member.utils.UserSha256;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("jeju/member/signup")
public class SignUpController {
	
	
	private final SignService signService;
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	@Autowired
	private void setBCryptPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}
	
	
	@GetMapping()
	public String signUpForm(Model model) {
		
		model.addAttribute("member" , new Member());
		return "signUp/signUp";
	}
	
	@PostMapping()
	public String signUp(@Valid @ModelAttribute Member member , BindingResult bindingResult  , RedirectAttributes redirectAttribute , Model model) {
				
		        //회원검증  필터 생성하기
				log.info("signUp member = {}" , member);
				if(bindingResult.hasErrors()) {
					log.info(" errors !");
					List<ObjectError> errors = bindingResult.getAllErrors();
					for(ObjectError error : errors) {
						log.info("error = {}" , error.getDefaultMessage());
					}
					return "signUp/signUp";
				}
				
				//성공시
				//비밀번호 엔크립션
				boolean singEmailCheck = signService.signEmailCheckSelect(member.getEmail());
				boolean singNickNameCheck = signService.signNickNameCheckSelect(member.getNickName());
				
				if(singEmailCheck) {
					model.addAttribute("errorAlert" , "error");
					return "signUp/signUp";
				}
				
				if(singNickNameCheck) {
					model.addAttribute("errorAlert" , "error");
					return "signUp/signUp";
				}
				
				
				String encryPwd = bcryptPasswordEncoder.encode(member.getPwd());
				member.setPwd(encryPwd);
				
				//가입
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
	
	@ResponseBody
	@GetMapping("nickCheck")
	public void nickNameCheck(String nickName, HttpServletResponse response) {
	//	log.info("nickName = {}" , nickName);
		String data = signService.nickNameCheckSelect(nickName);
		//log.info("emailCheck = {}" , email);
	//	log.info("data = {}" , data);
		try {
			response.setContentType("text/plain;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(data);
		} catch (IOException io) {
			
		}
		
	}
}
