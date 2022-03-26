package jejufriends.member.contol;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jejufriends.member.service.MemberPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/jejufriends/member/withdraw")
public class WithdrawController {
	
	
	@GetMapping
	public String MemberWithdraw() {
		return "mypage/withdraw";
	} 
}
