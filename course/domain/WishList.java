package jejufriends.course.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class WishList {
	private long winum;
	private long cnum;
	private String email;
	private String cname;
	private String cintro;
	private boolean exist;
	
	public WishList(long winum, long cnum, String email, String cname, String cintro, boolean exist) {
		this.winum = winum;
		this.cnum = cnum;
		this.email = email;
		this.cname = cname;
		this.cintro = cintro;
		this.exist = exist;
	}
}
