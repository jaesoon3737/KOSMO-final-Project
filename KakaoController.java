package members.member.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.domain.KakaoMember;
import members.member.service.KakaoLoginService;
import members.member.service.RestJsonService;

@Slf4j
@Controller
@RequestMapping()
@RequiredArgsConstructor
public class KakaoController {
	
	private final KakaoLoginService kakaoLoginService;
	private static AtomicLong atomic = new AtomicLong(1);
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	@Autowired
	private void setBCryptPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}
	
	
    @RequestMapping(value = "/jeju/member/getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(
			HttpServletRequest request) throws Exception {
		String reqUrl = 
				"https://kauth.kakao.com/oauth/authorize"
				+ "?response_type=code"
				+ "&client_id=4c98ff3317c00ffe3b366db42abb4c49"
				+ "&redirect_uri=http%3A%2F%2F127.0.0.1%3A8080%2Fjeju%2Fmember%2Fkakao";
		
		return reqUrl;
	}
	
  
	// 카카오 연동정보 조회
	@RequestMapping(value = "/jeju/member/kakao")
	public String oauthKakao(
			@RequestParam(value = "code", required = false) String code
			, Model model) throws Exception {
	
        String accessToken = kakaoLoginService.getReturnAccessToken(code);

        
        
        Map<String, Object> userInfo = kakaoLoginService.getLoginMemberInfo(accessToken);
        
        String kakaoId = userInfo.get("id").toString().substring(1);
        String kakaoName = userInfo.get("nickname").toString();
        String kakaoPwd = userInfo.get("kakaoPwd").toString();
        String encryPwd = bcryptPasswordEncoder.encode(kakaoPwd);
	
        //String kakaoRefresh = userInfo.get("refresh_token_expires_in").toString();
        String uuid = UUID.randomUUID().toString();
        
  
        long number = atomic.incrementAndGet();
        
        StringBuilder sbNickName = new StringBuilder();
        sbNickName.append(kakaoName);
        sbNickName.append(number);
        String kakaoNickName = sbNickName.toString();
        
        
        StringBuilder sbId = new StringBuilder();
        sbId.append(kakaoId);
        sbId.append("@kakaotalk.com");
        String kakaoIdEmailTrans = sbId.toString();
        /*
        log.info("kakaoRefresh = {}" , kakaoRefresh);
        log.info("kakaouuid = {}" , uuid );
        log.info("kakaoRefresh = {}" , kakaoId);
        log.info("kakaoRefresh = {}" , kakaoName);
        log.info("kakaoPwd = {}" , kakaoPwd);
        log.info("kakaoNickName = {}" , kakaoNickName);
        */
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
        	return "redirect:/jeju/user";
        } else {
        	kakaoLoginService.addMemberForKAKAO(kakaoMember);
        	List<GrantedAuthority> memberRole = new ArrayList<GrantedAuthority>();
        	memberRole.add(new SimpleGrantedAuthority("ROLE_USER"));
        	User user = new User(kakaoMember.getUsername(),"",memberRole);
        	Authentication auth = new UsernamePasswordAuthenticationToken(user, null , memberRole);
        	SecurityContextHolder.getContext().setAuthentication(auth);
        	return "redirect:/jeju/user";
        }
	}
 }