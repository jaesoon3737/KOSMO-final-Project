package members.member.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import members.member.domain.LoginMember;
import members.member.domain.Member;
import members.member.mapper.LoginMapper;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{
	
	private final LoginMapper loginMapper;

	@Override
	public Member loginId(LoginMember loginMember) {
		Member member = loginMapper.loginId(loginMember);
		return loginMapper.loginId(loginMember);
	}

	@Override
	public void loginFailCount(String userName) {
		loginMapper.loginFailCount(userName);
		
	}

	@Override
	public Integer checkFailCount(String userName) {
		// TODO Auto-generated method stub
		return loginMapper.checkFailCount(userName);
	}

	@Override
	public void disabledUser(String userName) {
		loginMapper.disabledUser(userName);
		
	}

}
