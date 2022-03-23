package members.member.service;

import members.member.domain.ForgetMember;

public interface ForgetPasswordMailService {
	String joinEmail(String email , Integer authNum);
	void mailSend(String setFrom, String toMail, String title, String Content);
	boolean findMember(String email);
	void updatePassword(ForgetMember forgetMember);
}
