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
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
    @RequestMapping(value = "/login/getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(
			HttpServletRequest request) throws Exception {
		String reqUrl = 
				"https://kauth.kakao.com/oauth/authorize"
				+ "?response_type=code"
				+ "&client_id=4c98ff3317c00ffe3b366db42abb4c49"
				+ "&redirect_uri=http%3A%2F%2F127.0.0.1%3A8080%2Fjeju%2Fmember%2Fkakao";
		
		return reqUrl;
	}
	
   
	// īī�� �������� ��ȸ
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
        
        StringBuilder sb = new StringBuilder();
        sb.append(kakaoName);
        sb.append(number);
        String kakaoNickName = sb.toString();
        /*
        log.info("kakaoRefresh = {}" , kakaoRefresh);
        log.info("kakaouuid = {}" , uuid );
        log.info("kakaoRefresh = {}" , kakaoId);
        log.info("kakaoRefresh = {}" , kakaoName);
        log.info("kakaoPwd = {}" , kakaoPwd);
        log.info("kakaoNickName = {}" , kakaoNickName);
        */
        /**
         *  checkSnsId = 1 �� ��� sns ���̵�
         *  checkSnsID = 0 �ϰ�� �Ϲ� ȸ�� db ����Ʈ 0
         * */
        KakaoMember kakaoMember = new KakaoMember(kakaoId, encryPwd , kakaoNickName , kakaoName, 1);
        // true �� ��� ����
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
	/*
    //��ū�߱�
	public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //  URL������ ����¿� ��� �� �� �ְ�, POST Ȥ�� PUT ��û�� �Ϸ��� setDoOutput�� true�� �����ؾ���.
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //	POST ��û�� �ʿ�� �䱸�ϴ� �Ķ���� ��Ʈ���� ���� ����
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=4c98ff3317c00ffe3b366db42abb4c49");  //������ �߱޹��� key
            sb.append("&redirect_uri=http%3A%2F%2F127.0.0.1%3A8080%2Fjeju%2Fmember%2Fkakao");     // ������ ������ ���� ���
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //    ��� �ڵ尡 200�̶�� ����
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //    ��û�� ���� ���� JSONŸ���� Response �޼��� �о����
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //    Gson ���̺귯���� ���Ե� Ŭ������ JSON�Ľ� ��ü ����
            com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return access_Token;
    }
    //����������ȸ
    public HashMap<String, Object> getUserInfo (String access_Token) {

        //    ��û�ϴ� Ŭ���̾�Ʈ���� ���� ������ �ٸ� �� �ֱ⿡ HashMapŸ������ ����
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    ��û�� �ʿ��� Header�� ���Ե� ����
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
            JsonElement element = parser.parse(result);
            
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            int id = element.getAsJsonObject().get("id").getAsInt();
            log.info("id = {}",id);
           // String id = ids.getAsString();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
         
         if(kakao_account.getAsJsonObject().get("email") != null) {
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            userInfo.put("email", email);
          }
            userInfo.put("accessToken", access_Token);
            userInfo.put("nickname", nickname);
            userInfo.put("id", id);
         

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userInfo;
    }*/
 }