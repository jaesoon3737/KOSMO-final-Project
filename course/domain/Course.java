package jejufriends.course.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Course {
	private long cnum;
	private String nick;
	private String cname;
	private String cintro;
	private String ctag;
	private long choosed;
	private String ccost;
	private long love;
	private long views;
	private int division;
	private String startdate;
	private String lastdate;
	private String cdate;
	
	public Course(long cnum, String nick, String cname, String cintro, String ctag, long choosed, String ccost,
			long love, long views, int division, String startdate, String lastdate, String cdate) {
		this.cnum = cnum;
		this.nick = nick;
		this.cname = cname;
		this.cintro = cintro;
		this.ctag = ctag;
		this.choosed = choosed;
		this.ccost = ccost;
		this.love = love;
		this.views = views;
		this.division = division;
		this.startdate = startdate;
		this.lastdate = lastdate;
		this.cdate = cdate;
	}
}
