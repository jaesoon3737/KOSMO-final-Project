package jejufriends.member.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import jejufriends.member.domain.Member;
import jejufriends.member.domain.TabooWord;
import jejufriends.member.repository.SignUpMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpMemberServiceImpl implements SignUpMemberService {
	
	private final SignUpMemberRepository signUpMemberRepository;
	private static List<TabooWord> taboo;
	
	@PostConstruct
	public void init() {
		taboo = signUpMemberRepository.tabooNickNameCheckSelect();
		log.warn("SignUpMemberServiceImpl PostConstruct error");
	}
	
	@Override
	public void addMember(Member member) {
		signUpMemberRepository.addMember(member);
	}


	@Override
	public String emailCheckSelect(String emailCheck) {
		String emailCheckFlag = signUpMemberRepository.emailCheckSelect(emailCheck);
		log.warn("emailCheckSelect error email = {}" , emailCheckFlag);
		if(emailCheckFlag == null) {
			return "ȸ�� ������ ������ ���̵��Դϴ�.";
		} else {
			return "�̹� ������� ���̵� �Դϴ�.";
		}
	}
	

	


	@Override
	public boolean signEmailCheckSelect(String email) {
		int emailCheckFlag= signUpMemberRepository.signEmailCheckSelect(email);
		boolean result = false;
		
		if(emailCheckFlag == 1) result = true;
		
		return result;
	}


	@Override
	public boolean signNickNameCheckSelect(String nickName) {
		int nickNameCheckFlag = signUpMemberRepository.signNickNameCheckSelect(nickName);
		
		boolean result = false;
		
		for(TabooWord tabooGet : taboo) {
			   String NickNameContainCheck = tabooGet.getTabooWordCheck();
			   if(nickName.contains(NickNameContainCheck)) {
				   result = true;
				   break;
			   }
		  }

		if( nickNameCheckFlag == 1) result = true;
		
		return result;
	}



}
