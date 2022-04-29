package soo.md.mapper;

import java.util.List;

import soo.md.domain.Landmark;
import soo.md.domain.LandmarkVo;
import soo.md.domain.LandmarkreviewVo;

public interface LandmarkMapper {
	List<Landmark> selectPerPage(LandmarkVo landmarkVo);
	Landmark selectBySeq(long lnum);
	long selectCount();
	Integer searchSelectSubjectCount(String surf);
	Integer searchSelectContentCount(String surf);
	
	List<Landmark> list();
	Landmark content(long lnum);
	void views(long lnum);
	void delete(long lnum);
	void insert(LandmarkreviewVo landmarkreviewVo);
	List<LandmarkreviewVo> select(long lnum);
	
	List<Landmark> selectSearchSubject(LandmarkVo landmarkVo);
	List<Landmark> selectSearchContent(LandmarkVo landmarkVo);
	List<Landmark> selectBySubject(String surf);
	List<Landmark> selectByContent(String surf);
	
	// 닉네임 조회
	String selectNick(String email);
}
