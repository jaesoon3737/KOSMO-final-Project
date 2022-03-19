package members.member.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.domain.Member;
import members.member.domain.TabooWord;
import members.member.mapper.SignMemberMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
	
	private final SignMemberMapper signMemberMapper;
	private static List<TabooWord> taboo;
	
	
	//의존성 주입시 한번만 만들고조회하려고만듬.
	@PostConstruct
	public void init() {
		//log.info("PostConstruct 실행시기 확인용 ");
		taboo = signMemberMapper.tabooNickNameCheckSelect();
	}
	
	@Override
	public void addMember(Member member) {
		signMemberMapper.addMember(member);
	}


	@Override
	public String emailCheckSelect(String emailCheck) {
		String emailCheckFlag= signMemberMapper.emailCheckSelect(emailCheck);
		if(emailCheckFlag == null) {
			return "회원 가입이 가능한 아이디입니다.";
		} else {
			return "이미 사용중인 아이디 입니다.";
		}
	}
	

	@Override
	public String nickNameCheckSelect(String nickName) {
		boolean tabooCheckBoolean = false;
		String nickNameCheckFlag = signMemberMapper.nickNameCheckSelect(nickName);	
		log.info("nickNameCheckFlag = {}" , nickNameCheckFlag);
		
		if(nickName.length() >= 5 && nickNameCheckFlag == null) {  
		   for(TabooWord tabooGet : taboo) {
			   String NickNameContainCheck = tabooGet.getTabooWordCheck();
			   if(nickName.contains(NickNameContainCheck)) {
				   tabooCheckBoolean = true;
				   break;
			   }
		   }
		   /*
		   HashMap<Integer , String> tabooNickNameCheckMap = 
		   Iterator<Integer> tabooKey = tabooNickNameCheckMap.keySet().iterator();		 
		   while(tabooKey.hasNext()) {
			   String tabooWord = tabooNickNameCheckMap.get(tabooKey.next());
			   tabooCheckBoolean = nickName.contains(tabooWord);
			   log.info("break");
			   //if(tabooCheckBoolean) break;
		   }
		   */
		   if(tabooCheckBoolean) {
				return "불건전한 닉네임입니다. 변경해주세요.";
		   } else {
				return "사용 가능한 닉네임입니다.";
		   }
		} else {
			return "사용 불가능한 닉네임입니다.";
		}
	}


	@Override
	public boolean signEmailCheckSelect(String email) {
		int emailCheckFlag= signMemberMapper.signEmailCheckSelect(email);
		boolean result = false;
		
		if(emailCheckFlag == 1) result = true;
		
		return result;
	}


	@Override
	public boolean signNickNameCheckSelect(String nickName) {
		int nickNameCheckFlag = signMemberMapper.signNickNameCheckSelect(nickName);
		
		boolean result = false;
		
		if( nickNameCheckFlag == 1) result = true;
		
		return result;
	}

}
