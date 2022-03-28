package jejufriends.member.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdatePassword {
	
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,200}$" , message="Ư������,����,���� �� �����Ͽ� 10~20�ڸ��� �Է��ϼ���")
	@NotEmpty(message="�ʼ� �Է� �׸��Դϴ�.")
	private String pwd;
	
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,200}$" , message="Ư������,����,���� �� �����Ͽ� 10~20�ڸ��� �Է��ϼ���")
	@NotEmpty(message="�ʼ� �Է� �׸��Դϴ�.")
	private String newPwd;
	private String email;
	
	
	
	
	public UpdatePassword(
			@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,200}$", message = "Ư������,����,���� �� �����Ͽ� 10~20�ڸ��� �Է��ϼ���") @NotEmpty(message = "�ʼ� �Է� �׸��Դϴ�.") String pwd,
			@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,200}$", message = "Ư������,����,���� �� �����Ͽ� 10~20�ڸ��� �Է��ϼ���") @NotEmpty(message = "�ʼ� �Է� �׸��Դϴ�.") String newPwd,
			String email) {
		super();
		this.pwd = pwd;
		this.newPwd = newPwd;
		this.email = email;
	}




	public UpdatePassword(
			@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,200}$", message = "Ư������,����,���� �� �����Ͽ� 10~20�ڸ��� �Է��ϼ���") @NotEmpty(message = "�ʼ� �Է� �׸��Դϴ�.") String pwd,
			@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,200}$", message = "Ư������,����,���� �� �����Ͽ� 10~20�ڸ��� �Է��ϼ���") @NotEmpty(message = "�ʼ� �Է� �׸��Դϴ�.") String newPwd) {
		super();
		this.pwd = pwd;
		this.newPwd = newPwd;
	}
		
		
}
