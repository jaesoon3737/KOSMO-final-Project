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
	
	
	//������ ���Խ� �ѹ��� �������ȸ�Ϸ�����.
	@PostConstruct
	public void init() {
		//log.info("PostConstruct ����ñ� Ȯ�ο� ");
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
			return "ȸ�� ������ ������ ���̵��Դϴ�.";
		} else {
			return "�̹� ������� ���̵� �Դϴ�.";
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
				return "�Ұ����� �г����Դϴ�. �������ּ���.";
		   } else {
				return "��� ������ �г����Դϴ�.";
		   }
		} else {
			return "��� �Ұ����� �г����Դϴ�.";
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
