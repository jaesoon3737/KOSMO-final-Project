package jejufriends.member.domain;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import org.springframework.format.annotation.DateTimeFormat;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {
	
	private Long idNumber;
	@NotEmpty(message="�ʼ� �Է� �׸��Դϴ�.")
	private String email;
	
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,200}$" , message="Ư������,����,���� �� �����Ͽ� 10~20�ڸ��� �Է��ϼ���")
	@NotEmpty(message="�ʼ� �Է� �׸��Դϴ�.")
	private String pwd;
	@NotEmpty(message="�ʼ� �Է� �׸��Դϴ�.")
	@Size(min=3 , message="3���� �̻� �Է��ϼ���.")
	private String nickName;
	@NotEmpty(message="�ʼ� �Է� �׸��Դϴ�.")
	private String name;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent( message="�ùٸ� ������ �Է����ּ���.")
	@NotNull( message="�ʼ� �Է� �׸��Դϴ�.")
	private Date birth;
	
	@Pattern(regexp = "^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$" , message="�޴�����ȣ�� �Է��ϼ���")
	@NotNull( message="�ʼ� �Է� �׸��Դϴ�.")
	private String phoneNumber;
	@NotEmpty(message="�ʼ� �Է� �׸��Դϴ�.")
	private String gender;
	private Integer enabled;
	private Integer cautionCount;
    private Integer checkSnsId;
	
	public Member(String email , String pwd) {
		this.email = email;
		this.pwd = pwd;
	}
	

	
	
	public Member( String email, String pwd, String nickName, String name, Date birth,
			String phoneNumber, String gender, Integer enabled , Integer checkSnsId) {
		super();
		this.email = email;
		this.pwd = pwd;
		this.nickName = nickName;
		this.name = name;
		this.birth = birth;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.enabled = enabled;
		this.checkSnsId = checkSnsId;
	}
	
	
	public Member(Long idNumber, String email, String pwd, String nickName, String name, Date birth,
			String phoneNumber, String gender, Integer enabled , Integer checkSnsId) {
		super();
		this.idNumber = idNumber;
		this.email = email;
		this.pwd = pwd;
		this.nickName = nickName;
		this.name = name;
		this.birth = birth;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.enabled = enabled;
		this.checkSnsId = checkSnsId;
	}
}
