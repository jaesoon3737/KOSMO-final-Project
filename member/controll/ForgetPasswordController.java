package jejufriends.member.controll;

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

/**
 * Commit Date : 2022.03.27
 * @author jaesoon
 * 		   
 * 			forgetPassword , findPassword
 * 				check Exist Member
 *				email Authentication Number 
 *				change Password
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("jejufriends/forgetPassword")
public class ForgetPasswordController {
	

	/**
	 * testCode©��  hashtable�� ����ȭ�� �ɷ��־ ���� ����. ���� ��Ƽ�����忡�� �������� ������������ ���������� ����. 
	 * Concurrent�� HashMap�� �ٸ��� �б�� ����ȭ�� �ɷ����� ������ put�� ����ȭ�� �ɷ��־ ��ó���� �Ǳ� ������ ��ũ�γ�����带 ���� ó�������ʰ� ��Ƽ ������ ȯ�濡�� �������� �����Ѵ�. 
	 * ��, get�� �� ���� �� �б��� �ൿ�� �� ������ ����ȭ�� ������� �ʴ´�.
	 * ���� �̸���ó���� Async �� ����Ͽ� ó���ϱ� ������ ��Ƽ������ ȯ�濡�� ��밡���� �ؽ����� ����߾�� �߱⿡ ��Ŀ��Ʈ �ؽ����� �����.
	 */
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
	 * 
	 * @param forgetMember : requester
	 * @param checkNumber  : email authentication number
	 * @param redirectAttribute : for fail = restPwd , success = findPassword 
	 * @return
	 */
	@PostMapping
	public String findPasswordMapping(@ModelAttribute ForgetMember forgetMember , @RequestParam("checkNumber")String checkNumber , RedirectAttributes redirectAttribute) {
		
			String forgetMemberEmail =  forgetMember.getEmail();
			boolean checkNumberValid = checkNumberValidation(checkNumber);
			
			if(!checkNumberValid) {
				redirectAttribute.addAttribute("errorAlert" , "errorNumber");
				redirectAttribute.addFlashAttribute("email" , forgetMember.getEmail());
				return "redirect:/jejufriends/forgetPassword/restPwd";
			}
			
			//exist = true
			boolean MemberExistCheck = forgetPasswordMailService.findByMember(forgetMemberEmail);	
			
			if(MemberExistCheck) {
				if(checkNumber.equals(matchingMap.get(forgetMemberEmail))) {
					redirectAttribute.addFlashAttribute("email" , forgetMember.getEmail());
					matchingMap.remove(forgetMemberEmail);
					return "redirect:/jejufriends/forgetPassword/findPassword";	
				} else {
					redirectAttribute.addAttribute("errorAlert" , "errorNumber");
					redirectAttribute.addFlashAttribute("email" , forgetMember.getEmail());
					return "redirect:/jejufriends/forgetPassword/restPwd";
				}
			} else {	
				redirectAttribute.addAttribute("errorAlert" , "errorEmail");
				return "redirect:/jejufriends/forgetPassword/restPwd";
			}
		
	}
	
	/**
	 * 
	 * @param forgetMember   : requester
	 * @param bindingResult  : password Validation
	 * @param model
	 * @return               : fail = findPassword , success = login
	 */
	@PostMapping("update")
	public String findPasswordUpdate(@Valid @ModelAttribute ForgetMember forgetMember ,
			BindingResult bindingResult , Model model) {
		String settingPassword = bcryptPasswordEncoder.encode(forgetMember.getPwd());

		if(bindingResult.hasErrors()) {		
			model.addAttribute("email" , forgetMember.getEmail());
			return "login/findPassword";
		}
		
		forgetMember.setPwd(settingPassword);
		forgetPasswordMailService.updatePassword(forgetMember);
		return "redirect:/jejufriends/login";
	}
	
	/**
	 * 
	 * @param model
	 * @param email      :  email authentication number for email
	 * @param errorAlert :  if not exist
	 * @return
	 */
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
	
	/**
	 * 
	 * @param emailForgetPasswordVlid : email , password Validation , using pattern
	 * @param bindingResult           : email , password Validation
	 * @param model
	 * @return
	 */
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
	
	/**
	 *  random authentication number create
	 * @return
	 */
	private String makeRandomNumber() {
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		
		return String.valueOf(checkNum);
	}
}
