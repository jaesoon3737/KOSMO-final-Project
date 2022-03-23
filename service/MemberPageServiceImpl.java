package members.member.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jdk.internal.org.jline.utils.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.domain.Member;
import members.member.mapper.MyPageMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPageServiceImpl implements MemberPageService{
	
	private final MyPageMapper myPageMapper;
	
	@Override
	public boolean myPagePwdCheck(String email, String pwd) {
		log.info("pwdCheck email = {}" , email);
		log.info("pwdCheck pwd = {}" , pwd);
		Member myPageCheckObj =	myPageMapper.myPagePwdCheck(email);
		
		if(myPageCheckObj.getPwd().equals(pwd)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Member userInfoSelect(String email) {
		//PhonNumber **
		Member member = myPageMapper.userInfoSelect(email);
		
		//kakao ȸ���̶�� ������ �ϰ� ����,
		if(member.getCheckSnsId() == 1 ) {
			member.setGender("īī��ȸ��");
		} else {
			if(member.getGender().equals("male")) {
				member.setGender("����");
			} else {
				member.setGender("����");
			}
		}
		return member;
	}

	@Override
	public String userInfoRole(String email) {
		return myPageMapper.userInfoRole(email);
	}

	@Override
	public String emailChangeStar(String email) {
		//eamil ** ó���ϱ� 
		String star = "*";
		int atIndex = email.indexOf("@");
		String emailSubStringOne = email.substring(0, 1);
		String emailSubStringOneAndAtIndex = email.substring(1, atIndex);
		int emailSubStringOneAndAtIndexLength = emailSubStringOneAndAtIndex.length();
		String emailSubStringAtIndexAndEnd = email.substring(atIndex);
		
		StringBuilder StarEmailStringBuilder = new StringBuilder();
		StarEmailStringBuilder.append(emailSubStringOne);
		
		for(int i = 0;i<emailSubStringOneAndAtIndexLength;i++) {
			StarEmailStringBuilder.append(star);
		}
		
		StarEmailStringBuilder.append(emailSubStringAtIndexAndEnd);
		
		return StarEmailStringBuilder.toString();
	}
	@Override
	public String phoneNumberChangeStar(String phoneNumber) {
		
		
		if(phoneNumber == null ) {
			return "";
		} else {
			String star = "*";
			String phoneNumberSubStringOne = phoneNumber.substring(0, 5);
			String phoneNumberSubStringTwo = phoneNumber.substring(9);
			
			StringBuilder StarPhoneNumberStringBuilder = new StringBuilder();
			StarPhoneNumberStringBuilder.append(phoneNumberSubStringOne);
			
			for(int i = 0; i < 4 ; i++) {
				StarPhoneNumberStringBuilder.append(star);
			}
			StarPhoneNumberStringBuilder.append(phoneNumberSubStringTwo);
			
			String phoneNumberChangeStar = StarPhoneNumberStringBuilder.toString();	
			return phoneNumberChangeStar;
		}
	
	}

}
