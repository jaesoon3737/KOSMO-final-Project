package soo.md.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Landmark {
	private long lnum;
	private String lname;
	private String lintro;
	private String lintro2;
	private long lcost;
	private String laddress;
	private String lphone;
	private String lopcl;
	private Date lclosed;
	private String lphoto;
	private String lphoto2;
	private String lphoto3;
	private String loriphoto;
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
