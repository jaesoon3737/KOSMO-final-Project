package soo.md.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import soo.md.domain.Activity;
import soo.md.domain.ActivityListResult;
import soo.md.domain.ActivitySearchListResult;
import soo.md.domain.ActivityVo;
import soo.md.domain.ActivityreviewVo;
import soo.md.mapper.ActivityMapper;

@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {
	private ActivityMapper activityMapper;
	
	@Override
	public ActivityListResult getActivityListResult(int cp, int ps) {
		long totalCount = activityMapper.selectCount();
		ActivityVo activityVo = new ActivityVo(cp, ps, "");
		List<Activity> list = activityMapper.selectPerPage(activityVo);
		
		return new ActivityListResult(cp, totalCount, ps, list);
	}
	
	@Override
	public Activity getActivity(long anum) {
		activityMapper.views(anum);;
		Activity activity = activityMapper.selectBySeq(anum);
		return activity;
	}

	@Override
	public Activity content(long anum) {
		return activityMapper.content(anum);	
	}

	@Override
	public void review(ActivityreviewVo activityreviewVo) {
		activityMapper.insert(activityreviewVo);
		
	}

	@Override
	public void remove(long arnum) {
		activityMapper.delete(arnum);		
	}

	@Override
	public ActivitySearchListResult getActivitySearchListResult(int cp, int ps, String surf, String search_key) {
		ActivityVo activityVo = new ActivityVo(cp, ps, surf);
		Integer totalCount;
		List<Activity> list;
		if("aname".equals(search_key)) { //콤보박스 제목,내용인지 분기 
			totalCount = activityMapper.searchSelectSubjectCount(surf);
			list = activityMapper.selectSearchSubject(activityVo);
		}else {
			totalCount = activityMapper.searchSelectContentCount(surf);
			list = activityMapper.selectSearchContent(activityVo);
		}
		if(totalCount == null) {
			totalCount = 0;
		}
		return new ActivitySearchListResult(cp, totalCount, ps, list, surf, search_key);		
	}

	@Override
	public List<Activity> selectBySubject(String surf) {
		return activityMapper.selectBySubject(surf);
	}

	@Override
	public List<Activity> selectByContent(String surf) {
		return activityMapper.selectByContent(surf);
	}

	@Override
	public List<ActivityreviewVo> reviewSelect(long anum) {
		return activityMapper.select(anum);
	}

	@Override
	public String findNick(String email) {
		return activityMapper.selectNick(email);
	}
}
