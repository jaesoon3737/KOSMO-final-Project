package jejufriends.member.contol;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String homes() {
		return "index";
	}
	@RequestMapping(value = "/jejufriends", method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	
}
