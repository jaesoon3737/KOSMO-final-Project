package jejufriends.course.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CourseLandMark {
	// 코스 컨텐츠 4가지에 인서트 하기 위한 VO
	private long clnum;
	private String lname;
	private int cday;
	private int order;
	private long cnum;
	private String contenttype;
	
	public CourseLandMark(long clnum, String lname, int cday, int order, long cnum, String contenttype) {
		this.clnum = clnum;
		this.lname = lname;
		this.cday = cday;
		this.order = order;
		this.cnum = cnum;
		this.contenttype = contenttype;
	}
}