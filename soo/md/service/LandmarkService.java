package soo.md.service;

import java.util.ArrayList;
import java.util.List;

import soo.md.domain.ActivityreviewVo;
import soo.md.domain.Landmark;
import soo.md.domain.LandmarkListResult;
import soo.md.domain.LandmarkSearchListResult;
import soo.md.domain.LandmarkreviewVo;

public interface LandmarkService {
	LandmarkListResult getLandmarkListResult(int cp, int ps);
	Landmark getLandmark(long lnum);
	void review(LandmarkreviewVo landmarkreviewVo);
	List<LandmarkreviewVo> reviewSelect(long lnum);
	Landmark content(long lnum);
	
	void remove(long lnum);
	
	LandmarkSearchListResult getLandmarkSearchListResult(int cp, int ps, String surf, String search_key);
	List<Landmark> selectBySubject(String surf);
	List<Landmark> selectByContent(String surf);
	
	String findNick(String email);
}
