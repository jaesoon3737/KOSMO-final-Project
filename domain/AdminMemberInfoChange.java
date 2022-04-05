package jejufriends.member.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminMemberInfoChange {
	
	private String email;
	@Size(min=3 , message="3���� �̻� �Է��ϼ���.")
	private String nickName;
	@Pattern(regexp = "^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$" , message="�޴�����ȣ�� �Է��ϼ���")
	private String phoneNumber;
	@NotEmpty(message="�ʼ� �Է� �׸��Դϴ�.")
	private String name;
	@NotEmpty(message="�ʼ� �Է� �׸��Դϴ�.")
	private String grade;
}
