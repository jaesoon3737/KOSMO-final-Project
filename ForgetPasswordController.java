package members.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.domain.EmailForgetPasswordVlid;
import members.member.domain.ForgetMember;
import members.member.domain.LoginMember;
import members.member.service.ForgetPasswordMailService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("jeju/forgetPassword")
public class ForgetPasswordController {
	
	//testCode©��
	private final ForgetPasswordMailService forgetPasswordMailService;
	private Map<String , Integer> matchingMap = new ConcurrentHashMap<String, Integer>();
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	private void setBCryptPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}
	
	/**
	 *      Ȯ�� �ؼ� ������ �����ֱ�
	 * */
	@PostMapping
	public String findPasswordMapping(@ModelAttribute ForgetMember forgetMember , @RequestParam("checkNumber")Integer checkNumber , RedirectAttributes redirectAttribute) {
		//log.info("email = {}" , forgetMember.getEmail());
		//log.info("pwd = {}" , forgetMember.getPwd());
		//log.info("randomNumber = {}" , checkNumber);
		
		String forgetMemberEmail =  forgetMember.getEmail();
		
		//log.info("randomNumbersssssss = {}" , matchingMap.get(forgetMemberEmail));

		//�����ϸ� true  integer Ÿ���� == ���ϸ� �ٸ��ٰ� ����. string ó��
		boolean MemberExistCheck = forgetPasswordMailService.findMember(forgetMemberEmail);	
		if(MemberExistCheck) {
			
			if(checkNumber.equals(matchingMap.get(forgetMemberEmail))) {
				log.info("üũ �ѹ��� �°� ���� = {}" , matchingMap.get(forgetMemberEmail));
				matchingMap.remove(forgetMemberEmail);
				return "redirect:/jeju/forgetPassword/findPassword/" + forgetMember.getEmail();
			} else {
				log.info("üũ �ѹ��� �ٸ��� ���� = {}" , matchingMap.get(forgetMemberEmail));
				
				redirectAttribute.addAttribute("errorAlert" , "errorNumber");
				redirectAttribute.addAttribute("email" , forgetMember.getEmail());
				return "redirect:/jeju/forgetPassword/restPwd";
			}
		} else {	
			log.info("���н� = {}" , forgetMember.getEmail());
			
			redirectAttribute.addAttribute("errorAlert" , "errorEmail");
			return "redirect:/jeju/forgetPassword/restPwd";
		}
	}
	
	/**
	 *      ��й�ȣ ������Ʈ
	 * */
	@PostMapping("update")
	public String findPasswordUpdate(@Valid @ModelAttribute ForgetMember forgetMember ,
			BindingResult bindingResult , Model model) {
		//log.info("email update = {}" , forgetMember.getEmail());
		//log.info("randomNumber update = {}" , forgetMember.getPwd());
		String settingPassword = bcryptPasswordEncoder.encode(forgetMember.getPwd());
		
		
		if(bindingResult.hasErrors()) {
			//log.info(" errors !");			
			model.addAttribute("email" , forgetMember.getEmail());
			return "login/findPassword";
		}
		
		forgetMember.setPwd(settingPassword);
		//log.info("randomNumber setting after= {}" , forgetMember.getPwd());
		forgetPasswordMailService.updatePassword(forgetMember);
		
		
		return "redirect:/jeju/member/login";
	}
	
	/**
	 *      ��й�ȣ ã�� �� ���� �� �����̷�Ʈ �� email ����
	 * */
	@GetMapping("/restPwd")
	public String restPwdForm(Model model ,@RequestParam(value="email" , required = false) String email ,@RequestParam(value="errorAlert" , required = false) String errorAlert) {
		
		if(email != null) {
			model.addAttribute("email" , email);
		}
		
		if(errorAlert != null) {
			if(errorAlert.equals("errorNumber")) {
				model.addAttribute("errorAlertNumber" , errorAlert);
			} else if (errorAlert.equals("errorEmail")) {
				model.addAttribute("errorAlertEmail" , errorAlert);
			} else {
				return "login/restPwd";
			}
		}
		return "login/restPwd";
	}
	

	@GetMapping("/findPassword/{email:.+}")
	public String findPasswordForm(@PathVariable String email , Model model) {
		//log.info("/findPassword email = {} " , email );
		model.addAttribute("forgetMember" , new ForgetMember());
		return "login/findPassword";
	}
	
	//�̸��� ���� ajax
	@GetMapping
	@ResponseBody
	public String mailCheck(@Valid EmailForgetPasswordVlid emailForgetPasswordVlid ,BindingResult bindingResult , Model model) {
	   //log.info("�̸��� ���� ��û�� ����!");
	   
		
		if(bindingResult.hasErrors()) {
			
			if(emailForgetPasswordVlid.getEmail() != null) {
				model.addAttribute("email" , emailForgetPasswordVlid.getEmail());	
			}
			return "login/restPwd";
		}
		
	   //log.info("���� �̸��� = {}", emailForgetPasswordVlid.getEmail());
	   
	   String email = emailForgetPasswordVlid.getEmail();
	   Integer randomNumber = makeRandomNumber();
	   matchingMap.put(email, randomNumber);
	   //log.info("����  �����μ�  = {}", randomNumber);
	   
	   return forgetPasswordMailService.joinEmail(email , randomNumber);				
	}
	
	private Integer makeRandomNumber() {
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		return checkNum;
	}
}
