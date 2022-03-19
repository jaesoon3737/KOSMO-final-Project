package members.member.mapper;

import members.member.domain.KakaoMember;
import members.member.domain.Member;

public interface KakaoMemberMapper {
	Integer kakaoTalkIdCheckExist(KakaoMember kakaoMember);
	void addMemberForKAKAO(KakaoMember kakaoMember);
}
