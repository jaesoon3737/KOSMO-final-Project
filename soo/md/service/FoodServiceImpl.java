package soo.md.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import soo.md.domain.Food;
import soo.md.domain.FoodListResult;
import soo.md.domain.FoodSearchListResult;
import soo.md.domain.FoodVo;
import soo.md.domain.reviewVo;
import soo.md.mapper.FoodMapper;

@Service
public class FoodServiceImpl implements FoodService{
	@Autowired
	private FoodMapper foodMapper;
	
	@Override
	public FoodListResult getFoodListResult(int cp, int ps) {
		long totalCount = foodMapper.selectCount();
		FoodVo foodVo = new FoodVo(cp, ps, ""); //파라미터 3개인 이유는 FoodVo에 파라미터 3개를 가진 생성자가 있는것이다.(FoodVo.java에서 안보이는 이유는 거기에 @allargsconstructor때문에 안보일 뿐이다)
		List<Food> list = foodMapper.selectPerPage(foodVo);
		
		return new FoodListResult(cp, totalCount, ps, list);
	}
	
	@Override
	public Food getFood(long fnum) {
		foodMapper.views(fnum);
		Food food = foodMapper.selectBySeq(fnum);
		return food;
	}

	@Override
	public Food content(long fnum) {
		return foodMapper.content(fnum);	
	}
	
	@Override
	public List<Food> selectBySubject(String surf) {
		return foodMapper.selectBySubject(surf);
	}
	@Override
	public List<Food> selectByContent(String surf) {
		return foodMapper.selectByContent(surf);
	}
	@Override
	public FoodSearchListResult getFoodSearchListResult(int cp, int ps, String surf, String search_key) {
		FoodVo foodVo = new FoodVo(cp, ps,surf);
		Integer totalCount;
		List<Food> list;
		if("fname".equals(search_key)) { //콤보박스 제목,내용인지 분기 
			totalCount = foodMapper.searchSelectSubjectCount(surf);
			list = foodMapper.selectSearchSubject(foodVo);
		}else {
			totalCount = foodMapper.searchSelectContentCount(surf);
			list = foodMapper.selectSearchContent(foodVo);
		}
		if(totalCount == null) {
			totalCount = 0;
		}
		return new FoodSearchListResult(cp, totalCount, ps, list, surf, search_key);		
	}
	@Override
	public void review(reviewVo reviewVo) {
		foodMapper.insert(reviewVo);
	}
	@Override
	public void remove(long frnum) {
		foodMapper.delete(frnum);	
	}
	@Override
	public List<reviewVo> reviewSelect(long fnum) {
		return foodMapper.select(fnum);
	}

	@Override
	public String findNick(String email) {
		return foodMapper.selectNick(email);
	}
}
