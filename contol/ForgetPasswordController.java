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
	
	//testCode©��
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
	 *      Ȯ�� �ؼ� ������ �����ֱ� ������ ���߿� �ذ��սô�. �޼ҵ� �ѹ��ϰ� ���� ��Ŀ��Ʈ�� ����� ���� �ٽ� ���µ�.
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
			
			//�����ϸ� true  integer Ÿ���� == ���ϸ� �ٸ��ٰ� ����. string ó��
			boolean MemberExistCheck = forgetPasswordMailService.findByMember(forgetMemberEmail);	
			if(MemberExistCheck) {
				if(checkNumber.equals(matchingMap.get(forgetMemberEmail))) {
					redirectAttribute.addFlashAttribute("email" , forgetMember.getEmail());
					matchingMap.remove(forgetMemberEmail);
					return "redirect:/jejufriends/forgetPassword/findPassword";	
				} else {
					redirectAttribute.addAttribute("errorAlert" , "errorNumber");
					redirectAttribute.addFlashAttribute("email" , forgetMember.getEmail());
					log.warn("�̸��� ������ȣ ���� checkNumber = {} " , checkNumber);
					log.warn("�̸��� ������ȣ ���� checkNumber12 = {} " , matchingMap.get(forgetMemberEmail));
					return "redirect:/jejufriends/forgetPassword/restPwd";
				}
			} else {	
				redirectAttribute.addAttribute("errorAlert" , "errorEmail");
				log.warn("�̸��� ������ȣ ���� ����");
				return "redirect:/jejufriends/forgetPassword/restPwd";
			}
		
	}
	
	/**
	 *      ��й�ȣ ������Ʈ
	 * */
	@PostMapping("update")
	public String findPasswordUpdate(@Valid @ModelAttribute ForgetMember forgetMember ,
			BindingResult bindingResult , Model model) {
		String settingPassword = bcryptPasswordEncoder.encode(forgetMember.getPwd());
		
		
		if(bindingResult.hasErrors()) {		
			model.addAttribute("email" , forgetMember.getEmail());
			return "login/findPassword";
		}
		log.warn("�̸��� ������Ʈ ����");
		forgetMember.setPwd(settingPassword);
		forgetPasswordMailService.updatePassword(forgetMember);
		return "redirect:/jejufriends/login";
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
	
	//�̸��� ���� ajax
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
