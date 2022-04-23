package jejufriends.member.domain;

import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailForgetPassword{
	
	@Email(message="�̸����� ��Ȯ�� �Է��ϼ���")
	private String email;

	public EmailForgetPassword(@Email(message = "�̸����� ��Ȯ�� �Է��ϼ���") String email) {
		super();
		this.email = email;
	}
	
	
}
