package soo.md.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

	private long hnum;
	private String hname;
	private String hintro;
	private String hintro2;
	private long hcost;
	private String haddress;
	private String hphone;
	private String hopcl;
	private String hbreak;
	private String hclosed;
	private String hphoto;
	private String hphoto2;
	private String hphoto3;
	private String horiphoto;
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
