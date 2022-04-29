package jejufriends.course.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CourseContent {
	// 코스 컨텐츠 테이블 4가지를 다루기 위한 VO
	private String contentname;
	private int cday;
	private int corder;
	private long cnum;
	private String contenttype;
	
	public CourseContent(String contentname, int cday, int corder, long cnum, String contenttype) {
		this.contentname = contentname;
		this.cday = cday;
		this.corder = corder;
		this.cnum = cnum;
		this.contenttype = contenttype;
	}
}
