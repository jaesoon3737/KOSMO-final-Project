package jejufriends.member.contol;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jejufriends.member.domain.LoginMember;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("jejufriends/login")
public class LoginController {
	
	@GetMapping()
	public String loginForm(Model model) {
		model.addAttribute("loginMember" , new LoginMember());
		return "login/login";
	}
	
}
