package soo.md.service;

import java.util.ArrayList;
import java.util.List;

import soo.md.domain.Food;
import soo.md.domain.FoodListResult;
import soo.md.domain.FoodSearchListResult;
import soo.md.domain.reviewVo;

public interface FoodService {
	FoodListResult getFoodListResult(int cp, int ps);
	Food getFood(long fnum);
	void review(reviewVo reviewVo);
	List<reviewVo> reviewSelect(long fnum);
	Food content(long fnum);
	
	void remove(long frnum);
	
	FoodSearchListResult getFoodSearchListResult(int cp, int ps, String surf, String search_key);
	List<Food> selectBySubject(String surf);
	List<Food> selectByContent(String surf);
	
	String findNick(String email);
}

