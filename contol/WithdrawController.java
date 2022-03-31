package jejufriends.member.contol;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
@RequestMapping("/jejufriends/member/withdraw")
public class WithdrawController {
	
	@Secured({"ROLE_USER" , "ROLE_ADMIN"}) 
	@GetMapping
	public String MemberWithdraw() {
		return "mypage/withdraw";
	} 
}
