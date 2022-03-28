package jejufriends.member.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginMember {
	
	@NotEmpty(message="�ʼ� �Է� �׸��Դϴ�.")
	@Email(message="�̸��Ϸ� �α����ϼ���.")
	private String email;
	@NotEmpty(message ="�ʼ� �Է� �׸��Դϴ�.")
	private String pwd;
	
	public LoginMember(String email, String pwd) {
		super();
		this.email = email;
		this.pwd = pwd;
	}
	
	
}
