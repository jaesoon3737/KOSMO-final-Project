package members.member.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import members.member.domain.ForgetMember;
import members.member.mapper.ForgetPasswordMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForgetPasswordMailServiceImpl implements ForgetPasswordMailService {
	

	private final JavaMailSender mailSender;
	private final ForgetPasswordMapper forgetPasswordMapper;
	
	//회원 가입 시 사용할 이메일 양식
	@Override
	@Async("executor")
	public String joinEmail(String email , Integer authNum ) {
		String setFrom = "jejufriends001@gmail.com"; //email-config에 설정한 자신의 이메일 주소를 입력.
		String toMail = email; //수신받을 이메일
		String title = "비밀번호 인증확인 번호입니다."; //이메일 제목
		String content = "홈페이지를 방문해주셔서 감사합니다." +
				"<br><br>" + 
				"인증 번호는 " + authNum + "입니다." + 
				"<br>" + 
				"해당 인증번호를 인증번호 확인란에 기입하여 주세요."; //이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
		return Integer.toString(authNum);
	}
	//비동기 메소드 관리하기  메소드 합쳐야할까 스레드가 다르게 배당되면?
	//이메일 전송 메서드
	@Override
	public void mailSend(String setFrom, String toMail, String title, String Content) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			//true 매개값을 전달하면 MultiPart 형식의 메세지 전달이 가능. 문자 인코딩 설정도 가능.
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			//true전달 -> html형식으로 전송, 작성하지 않으면 단순 텍스트로 전달.
			helper.setText(Content, true);
			mailSender.send(message);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean findMember(String email) {
		Integer checkExistMember = forgetPasswordMapper.findMember(email);
		//존재한다면 true
		if(checkExistMember >= 1) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public void updatePassword(ForgetMember forgetMember) {
		log.info("데헷 Impl");
		log.info("데헷 Impl id = {}" , forgetMember.getEmail());
		log.info("데헷 Impl pwd = {}" , forgetMember.getPwd());
		forgetPasswordMapper.updatePassword(forgetMember);
	}

}
