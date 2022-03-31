package jejufriends.member.contol;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jejufriends.member.domain.EmailForgetPassword;
import jejufriends.member.domain.ForgetMember;
import jejufriends.member.service.ForgetPasswordMailService;
import jejufriends.member.utils.CustomPattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("jejufriends/forgetPassword")
public class ForgetPasswordController {
	
	//testCode짤것
	private final ForgetPasswordMailService forgetPasswordMailService;
	private Map<String , String> matchingMap = new ConcurrentHashMap<String, String>();
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	 
	@Autowired
	private void setBCryptPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}
	
	private boolean checkNumberValidation(String checkNumber) {
		return CustomPattern.IS_ONLY_NUMBER.matcher(checkNumber).matches();
	}
	/**
	 *      확인 해서 변경폼 보내주기 문제네 나중에 해결합시다. 메소드 한번하고 나면 컨커런트라서 사라짐 값이 다시 리셋됨.
	 * */
	@PostMapping
	public String findPasswordMapping(@ModelAttribute ForgetMember forgetMember , @RequestParam("checkNumber")String checkNumber , RedirectAttributes redirectAttribute) {
		
			String forgetMemberEmail =  forgetMember.getEmail();
			boolean checkNumberValid = checkNumberValidation(checkNumber);
			
			if(!checkNumberValid) {
				redirectAttribute.addAttribute("errorAlert" , "errorNumber");
				redirectAttribute.addFlashAttribute("email" , forgetMember.getEmail());
				return "redirect:/jejufriends/forgetPassword/restPwd";
			}
			
			//존재하면 true  integer 타입은 == 로하면 다르다고 나옴. string 처럼
			boolean MemberExistCheck = forgetPasswordMailService.findByMember(forgetMemberEmail);	
			if(MemberExistCheck) {
				if(checkNumber.equals(matchingMap.get(forgetMemberEmail))) {
					redirectAttribute.addFlashAttribute("email" , forgetMember.getEmail());
					matchingMap.remove(forgetMemberEmail);
					return "redirect:/jejufriends/forgetPassword/findPassword";	
				} else {
					redirectAttribute.addAttribute("errorAlert" , "errorNumber");
					redirectAttribute.addFlashAttribute("email" , forgetMember.getEmail());
					log.warn("이메일 인증번호 오류 checkNumber = {} " , checkNumber);
					log.warn("이메일 인증번호 오류 checkNumber12 = {} " , matchingMap.get(forgetMemberEmail));
					return "redirect:/jejufriends/forgetPassword/restPwd";
				}
			} else {	
				redirectAttribute.addAttribute("errorAlert" , "errorEmail");
				log.warn("이메일 인증번호 실패 오류");
				return "redirect:/jejufriends/forgetPassword/restPwd";
			}
		
	}
	
	/**
	 *      비밀번호 업데이트
	 * */
	@PostMapping("update")
	public String findPasswordUpdate(@Valid @ModelAttribute ForgetMember forgetMember ,
			BindingResult bindingResult , Model model) {
		String settingPassword = bcryptPasswordEncoder.encode(forgetMember.getPwd());
		
		
		if(bindingResult.hasErrors()) {		
			model.addAttribute("email" , forgetMember.getEmail());
			return "login/findPassword";
		}
		log.warn("이메일 업데이트 오류");
		forgetMember.setPwd(settingPassword);
		forgetPasswordMailService.updatePassword(forgetMember);
		return "redirect:/jejufriends/login";
	}
	
	/**
	 *      비밀번호 찾는 폼 검증 및 리다이렉트 시 email 셋팅
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
	

	@GetMapping("/findPassword")
	public String findPasswordForm(HttpServletRequest request , Model model) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
	
		if(flashMap!=null) {
         String  email =(String)flashMap.get("email");
         model.addAttribute("email" , email);
        }
		model.addAttribute("forgetMember" , new ForgetMember());
		return "login/findPassword";
	}
	
	//이메일 인증 ajax
	@GetMapping
	@ResponseBody
	public String mailCheck(@Valid EmailForgetPassword emailForgetPasswordVlid ,BindingResult bindingResult , Model model) {
		if(bindingResult.hasErrors()) {
			
			if(emailForgetPasswordVlid.getEmail() != null) {
				model.addAttribute("email" , emailForgetPasswordVlid.getEmail());	
			}
			return "login/restPwd";
		}
		
	   String email = emailForgetPasswordVlid.getEmail();
	   String randomNumber = makeRandomNumber();
	   matchingMap.put(email, randomNumber);
	   
	   return forgetPasswordMailService.joinEmail(email , randomNumber);				
	}
	
	private String makeRandomNumber() {
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		
		return String.valueOf(checkNum);
	}
}
