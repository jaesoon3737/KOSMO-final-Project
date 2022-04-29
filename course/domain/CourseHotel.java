package jejufriends.course.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CourseHotel {
	// 코스 호텔에 인서트하기 위한 VO
	private long chnum;
	private String hname;
	private int cday;
	private int order;
	private long cnum;
	private String contentType;
	
	public CourseHotel(long chnum, String hname, int cday, int order, long cnum, String contentType) {
		this.chnum = chnum;
		this.hname = hname;
		this.cday = cday;
		this.order = order;
		this.cnum = cnum;
		this.contentType = contentType;
	}
}
