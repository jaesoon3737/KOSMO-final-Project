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
				//ȸ������  ���� �����ϱ�
				
				log.info("��� �ȳ� = {}" , member);
				/*
				if(!StringUtils.hasText(member.getEmail())) {
					bindingResult.addError
						(new FieldError("member" , "email" , member.getEmail() 
								, false , null , null, "���̵� �Է��ϼ���."));
				}
				
				if(!StringUtils.hasText(member.getPwd())) {
					bindingResult.addError
					(new FieldError("member" , "pwd" , member.getPwd() 
							, false , null , null, "��й�ȣ�� �Է��ϼ���."));
				}
				

				
				if(member.getBirth() == null || member.getBirth().toString().length() != 6 ) {
					bindingResult.addError
					(new FieldError("member" , "pwd" , member.getBirth() 
							, false , null , null, "��������� �Է��ϼ���."));
				}
				
				if(!StringUtils.hasText(member.getName())) {
					bindingResult.addError
					(new FieldError("member" , "name" , member.getName() 
							, false , null , null, "�̸��� �Է��ϼ���."));
				}
				
				if(!StringUtils.hasText(member.getNickName())) {
					bindingResult.addError
					(new FieldError("member" , "pwd" , member.getNickName() 
							, false , null , null, "�г����� �Է��ϼ���."));
				}
				
				if(bindingResult.hasErrors()) {
					log.info("error = {} " , bindingResult);
					return "signUp";
				}
				*/
				//������
				signService.addMember(member);
				
				//signServiceImpl.addMember(member);
				//redirectAttribute.addAttribute("status" , "ȸ�����Կ� �����Ͽ����ϴ�.");
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
