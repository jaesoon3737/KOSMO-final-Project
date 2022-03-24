package members.member.mapper;

import members.member.domain.LoginMember;
import members.member.domain.Member;

public interface LoginMapper {
	Member loginId(LoginMember loginMember);
	void loginFailCount(String userName);
	Integer checkFailCount(String userName);
	void disabledUser(String userName);
}
