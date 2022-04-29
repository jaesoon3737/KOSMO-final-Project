package soo.md.domain;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//AllArgsConstructor를 안쓴 이유는 다른 애들은 FaqServiceImpl에서 넘어온 값으로 초기화되지만, calTotalPageCount의 결과값이 totalPageCount에 초기화 되어야 하기 떄문

public class FoodSearchListResult {
	private List<Food> list;
	private int cp;
	private int ps;
	private Integer totalCount;
	private long totalPageCount;
	private String surf;
	private String search_key;
	
	public FoodSearchListResult(int cp, Integer totalCount,int ps, List<Food> list, String surf, String search_key) {
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
