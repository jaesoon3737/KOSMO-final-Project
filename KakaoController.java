package jejufriends.member.contol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import jejufriends.member.domain.KakaoMember;
import jejufriends.member.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/jejufriends/login")
@RequiredArgsConstructor
public class KakaoController {
	
	private final KakaoLoginService kakaoLoginService;
	private static AtomicLong atomic = new AtomicLong(1);
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	@Autowired
	private void setBCryptPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}
	
	
    @RequestMapping(value = "getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(
			HttpServletRequest request) throws Exception {
		String reqUrl = 
				"https://kauth.kakao.com/oauth/authorize"
				+ "?response_type=code"
				+ "&client_id=4c98ff3317c00ffe3b366db42abb4c49"
				+ "&redirect_uri=http%3A%2F%2F127.0.0.1%3A8080%2Fjejufriends%2Flogin%2Fkakao";
		
		return reqUrl;
	}
	

    
	// 카카오 연동정보 조회
	@RequestMapping(value = "kakao")
	public String oauthKakao(
			@RequestParam(value = "code", required = false) String code
			, Model model) throws Exception {
	
        String accessToken = kakaoLoginService.getReturnAccessToken(code);
        Map<String, Object> userInfo = kakaoLoginService.getLoginMemberInfo(accessToken);
        
        String kakaoId = userInfo.get("id").toString().substring(1);
        String kakaoName = userInfo.get("nickname").toString();
        String kakaoPwd = userInfo.get("kakaoPwd").toString();
        String encryPwd = bcryptPasswordEncoder.encode(kakaoPwd);

        long number = atomic.incrementAndGet();
        log.warn("kakao atomic error = {}" , number);
        
        StringBuilder sbNickName = new StringBuilder();
        sbNickName.append(kakaoName);
        sbNickName.append(number);
        String kakaoNickName = sbNickName.toString();
        
        StringBuilder sbId = new StringBuilder();
        sbId.append(kakaoId);
        sbId.append("@kakaotalk.com");
        String kakaoIdEmailTrans = sbId.toString();

        /**
         *  checkSnsId = 1 일 경우 sns 아이디
         *  checkSnsID = 0 일경우 일반 회원 db 디폴트 0
         * */
        KakaoMember kakaoMember = new KakaoMember(kakaoIdEmailTrans, encryPwd , kakaoNickName , kakaoName, 1);
        
        // true 일 경우 존재
        boolean KakaoMemberExist = kakaoLoginService.kakaoTalkIdCheckExist(kakaoMember);
        
        if(KakaoMemberExist) {
        	List<GrantedAuthority> memberRole = new ArrayList<GrantedAuthority>();
        	memberRole.add(new SimpleGrantedAuthority("ROLE_USER"));
        	User user = new User(kakaoMember.getUsername(),"",memberRole);
        	Authentication auth = new UsernamePasswordAuthenticationToken(user, null , memberRole);
        	SecurityContextHolder.getContext().setAuthentication(auth);
        	log.info("kakao login check = {}" , auth);
        	log.warn("kakao login auth check = {}" , auth);
        	return "redirect:/jejufriends";
        } else {
        	kakaoLoginService.addMemberForKAKAO(kakaoMember);
        	List<GrantedAuthority> memberRole = new ArrayList<GrantedAuthority>();
        	memberRole.add(new SimpleGrantedAuthority("ROLE_USER"));
        	User user = new User(kakaoMember.getUsername(),"",memberRole);
        	Authentication auth = new UsernamePasswordAuthenticationToken(user, null , memberRole);
        	SecurityContextHolder.getContext().setAuthentication(auth);
        	log.info("kakao login check = {}" , auth);
        	log.warn("kakao login auth check = {}" , auth);
        	return "redirect:/jejufriends";
        }
	}
 }