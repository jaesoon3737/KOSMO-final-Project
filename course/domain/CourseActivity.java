package jejufriends.course.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CourseActivity {
	// 코스 엑티비티에 인서트 하기 위한 vO
	private long chnum;
	private String aname;
	private int cday;
	private int order;
	private long cnum;
	private String contentType;
	
	public CourseActivity(long chnum, String aname, int cday, int order, long cnum, String contentType) {
		this.chnum = chnum;
		this.aname = aname;
		this.cday = cday;
		this.order = order;
		this.cnum = cnum;
		this.contentType = contentType;
	}
	
}
