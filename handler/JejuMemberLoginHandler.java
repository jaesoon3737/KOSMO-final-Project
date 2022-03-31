package jejufriends.member.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class JejuMemberLoginHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
	    List<String> roleNames = new ArrayList<String>();
		
	    // 권한 목록 만들기 
		authentication.getAuthorities().forEach(authority ->{
			 roleNames.add(authority.getAuthority());
		});
		
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/jejufriends/admin/memberManagement");
			return;
		}
		
		if(roleNames.contains("ROLE_SUPERADMIN")) {
			response.sendRedirect("/jejufriends/admin/memberManagement");
			return;
		}
		
		if(roleNames.contains("ROLE_USER")) {
			response.sendRedirect("/jejufriends");
			return;
		}
		
		response.sendRedirect("/");
	}
	
	

}