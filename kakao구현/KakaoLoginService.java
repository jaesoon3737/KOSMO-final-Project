package members.member.service;

import java.util.Map;

import members.member.domain.KakaoMember;
import members.member.domain.Member;

public interface KakaoLoginService {
	String getReturnAccessToken(String code);
	Map<String, Object> getLoginMemberInfo(String accessToken);
	boolean kakaoTalkIdCheckExist(KakaoMember kakaoMember);
	void addMemberForKAKAO(KakaoMember kakaoMember);
}
