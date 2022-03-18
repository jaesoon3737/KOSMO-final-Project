package members.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.domain.Member;
import members.member.service.LoginService;
import members.member.service.MemberPageService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("jeju/member/mypage")
public class MemberPageController {
		
		private final MemberPageService memberPageService;
		
		@GetMapping("check/{email}")
		public String myPageFormCheck(@PathVariable String email) {
			return "myPage/myPageCheck";
		}
		
		@GetMapping("myPage/{email}")
		public String myPageForm(@PathVariable String email) {
			return "myPage/myPage";
		}
		
		@PostMapping("form")
		public String myPageFormCheck(@RequestParam String pwd ,@RequestParam String email ,Model model) {
			
			boolean myPageFormCheckPassword = memberPageService.myPagePwdCheck(email, pwd);
			if(myPageFormCheckPassword) {
				return "redirect:/jeju/member/mypage/myPage/" + email;
			} else {
				return "redirect:/jeju/member/mypage/check/" + email;
			}
		}
}
