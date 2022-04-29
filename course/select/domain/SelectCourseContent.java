package jejufriends.course.select.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SelectCourseContent {
	private int cday;
	private int corder;
	private String contentname; 
	private String contentintro;
	private String contentaddress;
	private String contentopcl;
	private String contentbreak;
	private String contentphoto;
	private String xlocation;
	private String ylocation;
	private String contenttype;
	
	public SelectCourseContent(int cday, int corder, String contentname, String contentintro, String contentaddress,
			String contentopcl, String contentbreak, String contentphoto, String xlocation, 
			String ylocation, String contenttype) {
		this.cday = cday;
		this.corder = corder;
		this.contentname = contentname;
		this.contentintro = contentintro;
		this.contentaddress = contentaddress;
		this.contentopcl = contentopcl;
		this.contentbreak = contentbreak;
		this.contentphoto = contentphoto;
		this.xlocation = xlocation;
		this.ylocation = ylocation;
		this.contenttype = contenttype;
	}
}
