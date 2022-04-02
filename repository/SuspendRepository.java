package jejufriends.member.repository;

import java.util.List;

import jejufriends.member.domain.Member;
import jejufriends.member.domain.TabooContainsMember;

public interface SuspendRepository {
	Integer suspendAccount(Integer idNumber);
	Integer suspendAccountEnable(Integer idNumber);
	Integer cautionAccount(Integer idNumber);
	Integer cautionAccountMinus(Integer idNumber);
	Member findMember(Integer idNumber);
	Integer tabooContainsMemberForDefaultNickNameChange(List<TabooContainsMember> memberList);
}
