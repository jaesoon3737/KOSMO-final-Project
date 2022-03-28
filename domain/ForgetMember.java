package jejufriends.member.domain;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ForgetMember {
	
	private String email;
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,200}$" , message="Ư������,����,���� �� �����Ͽ� 10~20�ڸ��� �Է��ϼ���")
	private String pwd;
	
	public ForgetMember(String email) {
		super();
		this.email = email;
	}

	public ForgetMember(String email, String pwd) {
		super();
		this.email = email;
		this.pwd = pwd;
	}
	
	
	
}
