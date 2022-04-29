package soo.md.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelSearchListResult {
	private List<Hotel> list;
	private int cp;
	private int ps;
	private Integer totalCount;
	private long totalPageCount;
	private String surf;
	private String search_key;
	
	public HotelSearchListResult(int cp, Integer totalCount,int ps, List<Hotel> list, String surf, String search_key) {
		this.cp = cp;
		this.totalCount = totalCount;
		this.ps = ps;
		this.list = list;
		this.surf = surf;
		this.search_key = search_key;
		this.totalPageCount = calTotalPageCount();
	}
	private long calTotalPageCount() {
		long tpc = totalCount/ps; 
		if(totalCount%ps != 0) tpc++;
		
		return tpc;
	}
}
