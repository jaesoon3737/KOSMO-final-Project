package members.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.service.LoginService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("jeju/admin")
public class AdminController {
	
	
	@GetMapping()
	public String adminForm() {
		return "admin/sampleAdmin";
	} 
	
	
}
