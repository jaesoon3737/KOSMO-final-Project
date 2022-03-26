package jejufriends.member.contol;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jejufriends.member.domain.DeleteUser;
import jejufriends.member.domain.UpdatePassword;
import jejufriends.member.domain.UserInfoChange;
import jejufriends.member.service.MemberPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/jejufriends/rest")
public class MyPageRestAjaxController {
	
	private final MemberPageService memberPageService;
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	@Autowired
	private void setBCryptPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}
	
	@Secured("ROLE_USER")
	@PostMapping("updateinfo")
	public String userInfoUpdateAjax(@Valid @ModelAttribute UserInfoChange userInfoChange , BindingResult bindingResult,  Principal principal){
		String email = principal.getName();
		userInfoChange.setEmail(email);
		
		if(bindingResult.hasErrors()) {	
			return "4";
		}
		
		int resultInt = memberPageService.userInfoUpdate(userInfoChange);
		String result =String.valueOf(resultInt);
		
		return result;
	}
	
	@Secured("ROLE_USER") 
	@PostMapping("userdelete")
	public String userDeleteAjax(@Valid @RequestBody DeleteUser deleteUser , BindingResult bindingResult,
										Principal principal , HttpServletRequest request , 
												Authentication authentication) throws IOException, ServletException {
		String email = principal.getName();
		String checkPwd = memberPageService.userPasswordSearch(email);
		String pwd = deleteUser.getPwd();
		
		if(bindingResult.hasErrors()) {	
				return "3";
		}
		
		boolean pwdMatch = bcryptPasswordEncoder.matches(pwd , checkPwd);
		if(pwdMatch) {
			int result = memberPageService.userDelete(email);
	        if (authentication != null && authentication.getDetails() != null) {
	            try {      
	                 request.getSession().invalidate();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } 
	        
	        
			return String.valueOf(result);
		} else {
			return "2";
		}

	}
	
	@Secured("ROLE_USER") 
	@PostMapping("updatepassword")
	public String userUpdatePassword(@Valid @ModelAttribute UpdatePassword updatePassword ,
			BindingResult bindingResult , Principal principal , Model model) {
			//여기 여쭤볼것
			if(bindingResult.hasErrors()) {	
				List<ObjectError> errors = bindingResult.getAllErrors();
				for(ObjectError error : errors) {
					log.warn("error = {}" , error.getDefaultMessage());
					if(error.getDefaultMessage().contentEquals("특수문자,영어,숫자 를 조합하여 10~20자리를 입력하세요")) {
						return "2";
					} else {
						return "3";
					}
				}
				
			}
			String email = principal.getName();
			updatePassword.setEmail(email);
			
			String oldUserPwd = memberPageService.userPasswordSearch(email);
			String updatePasswordEncypt = bcryptPasswordEncoder.encode(updatePassword.getNewPwd());
			boolean passwordValid = bcryptPasswordEncoder.matches(updatePassword.getPwd() , oldUserPwd);
			
			if(passwordValid) {
				updatePassword.setPwd(updatePasswordEncypt);
				memberPageService.updatePasswordMemberInfo(updatePassword);
				return "1";
			} else {
				return "4";
			}
	}
}
