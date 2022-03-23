package members.member.service;

import members.member.domain.Member;

public interface MemberPageService {
	boolean myPagePwdCheck(String email , String pwd);
	Member userInfoSelect(String email);
	String userInfoRole(String email);
	String emailChangeStar(String email);
	String phoneNumberChangeStar(String phoneNumber);
}
