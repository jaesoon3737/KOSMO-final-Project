package soo.md.domain;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivityListResult {
	private List<Activity> list;
	private int cp;
	private int ps;
	private long totalCount;
	private long totalPageCount;
	
	public ActivityListResult(int cp, long totalCount, int ps, List<Activity> list) {
		this.cp = cp;
		this.totalCount = totalCount;
		this.ps = ps;
		this.list = list;
		this.totalPageCount = calTotalPageCount();
		
	}
	
	private long calTotalPageCount() {
		long tpc = totalCount/ps;
		if(totalCount%ps != 0) {
			tpc ++;
		}
		return tpc;
		
	}
}
