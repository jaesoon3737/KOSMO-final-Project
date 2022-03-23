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
	
	//ȸ�� ���� �� ����� �̸��� ���
	@Override
	@Async("executor")
	public String joinEmail(String email , Integer authNum ) {
		String setFrom = "jejufriends001@gmail.com"; //email-config�� ������ �ڽ��� �̸��� �ּҸ� �Է�.
		String toMail = email; //���Ź��� �̸���
		String title = "��й�ȣ ����Ȯ�� ��ȣ�Դϴ�."; //�̸��� ����
		String content = "Ȩ�������� �湮���ּż� �����մϴ�." +
				"<br><br>" + 
				"���� ��ȣ�� " + authNum + "�Դϴ�." + 
				"<br>" + 
				"�ش� ������ȣ�� ������ȣ Ȯ�ζ��� �����Ͽ� �ּ���."; //�̸��� ���� ����
		mailSend(setFrom, toMail, title, content);
		return Integer.toString(authNum);
	}
	//�񵿱� �޼ҵ� �����ϱ�  �޼ҵ� ���ľ��ұ� �����尡 �ٸ��� ���Ǹ�?
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
			//true���� -> html�������� ����, �ۼ����� ������ �ܼ� �ؽ�Ʈ�� ����.
			helper.setText(Content, true);
			mailSender.send(message);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean findMember(String email) {
		Integer checkExistMember = forgetPasswordMapper.findMember(email);
		//�����Ѵٸ� true
		if(checkExistMember >= 1) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public void updatePassword(ForgetMember forgetMember) {
		log.info("���� Impl");
		log.info("���� Impl id = {}" , forgetMember.getEmail());
		log.info("���� Impl pwd = {}" , forgetMember.getPwd());
		forgetPasswordMapper.updatePassword(forgetMember);
	}

}
