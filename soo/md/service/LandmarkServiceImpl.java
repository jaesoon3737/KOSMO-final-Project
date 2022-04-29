package soo.md.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import soo.md.domain.Landmark;
import soo.md.domain.LandmarkListResult;
import soo.md.domain.LandmarkSearchListResult;
import soo.md.domain.LandmarkVo;
import soo.md.domain.LandmarkreviewVo;
import soo.md.mapper.LandmarkMapper;


@Service
@AllArgsConstructor
public class LandmarkServiceImpl implements LandmarkService {
	private LandmarkMapper landmarkMapper;
	
	@Override
	public LandmarkListResult getLandmarkListResult(int cp, int ps) {
		long totalCount = landmarkMapper.selectCount();
		LandmarkVo landmarkVo = new LandmarkVo(cp, ps, "");
		List<Landmark> list = landmarkMapper.selectPerPage(landmarkVo);
		
		return new LandmarkListResult(cp, totalCount, ps, list);
	}
	
	@Override
	public Landmark getLandmark(long lnum) {
		landmarkMapper.views(lnum);
		Landmark landmark = landmarkMapper.selectBySeq(lnum);
		return landmark;
	}

	@Override
	public Landmark content(long lnum) {
		return landmarkMapper.content(lnum);	
	}
	
	@Override
	public void review(LandmarkreviewVo landmarkreviewVo) {
		landmarkMapper.insert(landmarkreviewVo);
		
	}
	@Override
	public void remove(long lrnum) {
		landmarkMapper.delete(lrnum);
	}
	
	@Override
	public LandmarkSearchListResult getLandmarkSearchListResult(int cp, int ps, String surf, String search_key) {
		LandmarkVo landmarkVo = new LandmarkVo(cp, ps, surf);
		Integer totalCount;
		List<Landmark> list;
		if("lname".equals(search_key)) { //콤보박스 제목,내용인지 분기 
			totalCount = landmarkMapper.searchSelectSubjectCount(surf);
			list = landmarkMapper.selectSearchSubject(landmarkVo);
		}else {
			totalCount = landmarkMapper.searchSelectContentCount(surf);
			list = landmarkMapper.selectSearchContent(landmarkVo);
		}
		if(totalCount == null) {
			totalCount = 0;
		}
		return new LandmarkSearchListResult(cp, totalCount, ps, list, surf, search_key);		
	}

	@Override
	public List<Landmark> selectBySubject(String surf) {
		return landmarkMapper.selectBySubject(surf);
	}

	@Override
	public List<Landmark> selectByContent(String surf) {
		return landmarkMapper.selectByContent(surf);
	}

	@Override
	public List<LandmarkreviewVo> reviewSelect(long lnum) {
		return landmarkMapper.select(lnum);
	}

	@Override
	public String findNick(String email) {
		return landmarkMapper.selectNick(email);
	}

}
