package soo.md.mapper;

import java.util.ArrayList;
import java.util.List;

import soo.md.domain.Food;
import soo.md.domain.FoodVo;
import soo.md.domain.reviewVo;

public interface FoodMapper {
	List<Food> selectPerPage(FoodVo foodVo);
	Food selectBySeq(long fnum);
	long selectCount();
	Integer searchSelectSubjectCount(String surf);
	Integer searchSelectContentCount(String surf);
	
	
	List<Food> list();
	Food content(long fnum);
	void views(long fnum);
	void delete(long frnum);
	void insert(reviewVo reviewVo);
	List<reviewVo> select(long fnum);
	
	List<Food> selectSearchSubject(FoodVo foodVo);
	List<Food> selectSearchContent(FoodVo foodVo);
	List<Food> selectBySubject(String surf);
	List<Food> selectByContent(String surf);
	
	// 닉네임 조회
	String selectNick(String email);
}
