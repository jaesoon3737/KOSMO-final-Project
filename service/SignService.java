package members.member.service;


import members.member.domain.Member;

public interface SignService {
	void addMember(Member member);
	String emailCheckSelect(String emailCheck);
	boolean signEmailCheckSelect(String email);
	boolean signNickNameCheckSelect(String nickName);
	String nickNameCheckSelect(String nickName);
}
