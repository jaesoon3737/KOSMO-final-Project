package soo.md.mapper;

import java.util.List;

import jejufriends.member.domain.Member;
import soo.md.domain.Activity;
import soo.md.domain.ActivityVo;

import soo.md.domain.ActivityreviewVo;



public interface ActivityMapper {
	List<Activity> selectPerPage(ActivityVo activtyVo);
	Activity selectBySeq(long anum);
	long selectCount();
	Integer searchSelectSubjectCount(String surf);
	Integer searchSelectContentCount(String surf);
	
	List<Activity> list();
	Activity content(long anum);
	void views(long anum);
	void delete(long anum);
	void insert(ActivityreviewVo activityreviewVo);
	List<ActivityreviewVo> select(long anum);
	
	List<Activity> selectSearchSubject(ActivityVo activityVo);
	List<Activity> selectSearchContent(ActivityVo activityVo);
	List<Activity> selectBySubject(String surf);
	List<Activity> selectByContent(String surf);
	
	// 닉네임 조회
	String selectNick(String email);
}
