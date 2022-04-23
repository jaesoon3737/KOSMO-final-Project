package jejufriends.member.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jejufriends.member.domain.ForgetMember;
import jejufriends.member.repository.ForgetPasswordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class ForgetPasswordMailServiceImpl implements ForgetPasswordMailService {
	
	private final JavaMailSender mailSender;
	private final ForgetPasswordRepository forgetPasswordRepository;
	
	//ȸ�� ���� �� ����� �̸��� ��� �ʹ������� �񵿱� 
	@Override
	@Async("executor")
	public String joinEmail(String email , String authNum ) {
		String setFrom = "jejufriends001@gmail.com"; //email-config�� ������ �ڽ��� �̸��� �ּҸ� �Է�.
		String toMail = email; //���Ź��� �̸���
		String title = "Jeuju Frirends ��й�ȣ ���� ������ȣ�Դϴ�."; //�̸��� ����
		String content = "���� Jeuju Frirends Ȩ�������� �̿����ּż� �����մϴ�." +
				"<br><br>" + 
				"�Է��Ͻ�" + 
				"���� ��ȣ�� " + authNum + "�Դϴ�." + 
				"<br>" + 
				"�ش� ������ȣ�� ������ȣ Ȯ�ζ��� �����Ͽ� �ּ���."; 
		mailSend(setFrom, toMail, title, content);
		return authNum;
	}
	
	//�̸��� ���� �޼���
	@Override
	public void mailSend(String setFrom, String toMail, String title, String Content) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			//true �Ű����� �����ϸ� MultiPart ������ �޼��� ������ ����. ���� ���ڵ� ������ ����.
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(Content, true);
			mailSender.send(message);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean findByMember(String email) {
		Integer checkExistMember = forgetPasswordRepository.findMember(email);
		//�����Ѵٸ� true
		if(checkExistMember >= 1) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public void updatePassword(ForgetMember forgetMember) {
		log.warn("ForgetPassword update Password error pwd = {}" , forgetMember.getPwd());
		forgetPasswordRepository.updatePassword(forgetMember);
	}

}
