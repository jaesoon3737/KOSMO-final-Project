package soo.md.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

	private long fnum;
	private String fname;
	private String fintro;
	private String fintro2;
	private String fmenu;
	private String fmenu2;
	private String fmenu3;
	private long fcost;
	private long fcost2;
	private long fcost3;
	private String faddress;
	private String fphone;
	private String fopcl;
	private String fbreak;
	private Date fclosed;
	private String fphoto;
	private String fphoto2;
	private String fphoto3;
	private String foriphoto;
	private long choosed;
	private String review;
	private long star;
	private long views;
	private int division;
	private long rnum;
	private long hits;
	private String search_key;
	private String surf;
	private String xlocation;
	private String ylocation;
	
}
