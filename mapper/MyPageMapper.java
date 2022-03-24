package members.member.mapper;

import members.member.domain.Member;

public interface MyPageMapper {
	Member myPagePwdCheck(String email);
	Member userInfoSelect(String email);
	String userInfoRole(String email);
}
