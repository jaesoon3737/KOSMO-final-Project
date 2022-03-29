package jejufriends.member.repository;


import org.springframework.stereotype.Repository;

import jejufriends.member.domain.Member;
import jejufriends.member.domain.UpdatePassword;
import jejufriends.member.domain.UserInfoChange;

@Repository
public interface MemberPageRepository {
	Member myPagePwdCheck(String email);
	Member userInfoSelect(String email);
	String userInfoRole(String email);
	Integer updatePasswordMemberInfo(UpdatePassword updatePassword);
	String userPasswordSearch(String email);
	int userInfoUpdate(UserInfoChange userInfoChange);
	int userDelete(String email);
}
