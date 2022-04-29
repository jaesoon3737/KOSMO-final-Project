package soo.md.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

public class LandmarkVo {
	private int cp = 1;
	private int ps = 5;
	private String surf;
	
	public int getStartRow() {
		return (cp-1)*ps;
	}
	
	public int getEndRow() {
		return cp*ps;
	}
	
	public String getSurf() {
		return surf;
	}
}
