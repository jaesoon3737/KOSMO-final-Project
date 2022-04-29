package soo.md.service;

import java.util.List;

import soo.md.domain.Activity;
import soo.md.domain.ActivityListResult;
import soo.md.domain.ActivitySearchListResult;
import soo.md.domain.ActivityreviewVo;
import soo.md.domain.Food;
import soo.md.domain.FoodSearchListResult;
import soo.md.domain.reviewVo;

public interface ActivityService {
	ActivityListResult getActivityListResult(int cp, int ps);
	Activity getActivity(long anum);
	void review(ActivityreviewVo activityreviewVo);
	List<ActivityreviewVo> reviewSelect(long anum);
	Activity content(long anum);
	
	void remove(long anum);
	
	ActivitySearchListResult getActivitySearchListResult(int cp, int ps, String surf, String search_key);
	List<Activity> selectBySubject(String surf);
	List<Activity> selectByContent(String surf);
	
	String findNick(String email);
}
