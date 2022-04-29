package jejufriends.community.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Community {
	private long comnum;
	private String email;
	private String nick;
	private String comsubject;
	private String comcontent;
	private String kategorie;
	private String comtag;
	private String comphoto;
	private String comoriphoto;
	private long views;
	private long love;
	private String comdate;
	private String comupdate;
	private int division;
	
	public Community(long comnum, String email, String nick, String comsubject, String comcontent, String kategorie, String comtag,
			String comphoto, String comoriphoto, long views, long love, String comdate, String comupdate, int division) {
		this.comnum = comnum;
		this.email = email;
		this.nick = nick;
		this.comsubject = comsubject;
		this.comcontent = comcontent;
		this.kategorie = kategorie;
		this.comtag = comtag;
		this.comphoto = comphoto;
		this.comoriphoto = comoriphoto;
		this.views = views;
		this.love = love;
		this.comdate = comdate;
		this.comupdate = comupdate;
		this.division = division;
	}
}
