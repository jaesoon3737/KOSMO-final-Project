package members.member.mapper;



import java.util.List;
import java.util.Map;

import members.member.domain.Member;
import members.member.domain.TabooWord;

public interface SignMemberMapper {
	void addMember(Member member);
	String emailCheckSelect(String emailCheck);
	Integer signEmailCheckSelect(String emailCheck);
	Integer signNickNameCheckSelect(String nickNameCheck);
	String nickNameCheckSelect(String nickNameCheck);
	List<TabooWord> tabooNickNameCheckSelect();
}
