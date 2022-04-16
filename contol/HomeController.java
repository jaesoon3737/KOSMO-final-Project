package jejufriends.member.contol;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping(value = "/jejufriends", method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = "/jejufriends/withdraw/withdrawClear")
	public String withdrawClear() {
		return "withdraw/withdrawClear";
	}
	
	@RequestMapping(value = "/jejufriends/contentInsert")
	public String contentInsertForm() {
		return "contentInsert/contentInsertForm";
	}
}
