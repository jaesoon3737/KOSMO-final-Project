package members.member.mapper;

import members.member.domain.ForgetMember;

public interface ForgetPasswordMapper {
	Integer findMember(String email);
	void updatePassword(ForgetMember forgetMember);
}
