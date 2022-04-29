package soo.md.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import soo.md.domain.Hotel;
import soo.md.domain.HotelListResult;
import soo.md.domain.HotelSearchListResult;
import soo.md.domain.HotelVo;
import soo.md.domain.HotelreviewVo;
import soo.md.mapper.HotelMapper;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {
	private HotelMapper hotelMapper;
	
	@Override
	public HotelListResult getHotelListResult(int cp, int ps) {
		long totalCount = hotelMapper.selectCount();
		HotelVo hotelVo = new HotelVo(cp, ps, "");
		List<Hotel> list = hotelMapper.selectPerPage(hotelVo);
		
		return new HotelListResult(cp, totalCount, ps, list);
	}
	
	@Override
	public Hotel getHotel(long hnum) {
		hotelMapper.views(hnum);;
		Hotel hotel = hotelMapper.selectBySeq(hnum);
		return hotel;
	}

	@Override
	public Hotel content(long hnum) {
		return hotelMapper.content(hnum);	
	}

	@Override
	public void review(HotelreviewVo hotelreviewVo) {
		hotelMapper.insert(hotelreviewVo);
		
	}

	@Override
	public void remove(long hrnum) {
		hotelMapper.delete(hrnum);		
	}

	@Override
	public HotelSearchListResult getHotelSearchListResult(int cp, int ps, String surf, String search_key) {
		HotelVo hotelVo = new HotelVo(cp, ps, surf);
		Integer totalCount;
		List<Hotel> list;
		if("hname".equals(search_key)) { //콤보박스 제목,내용인지 분기 
			totalCount = hotelMapper.searchSelectSubjectCount(surf);
			list = hotelMapper.selectSearchSubject(hotelVo);
		}else {
			totalCount = hotelMapper.searchSelectContentCount(surf);
			list = hotelMapper.selectSearchContent(hotelVo);
		}
		if(totalCount == null) {
			totalCount = 0;
		}
		return new HotelSearchListResult(cp, totalCount, ps, list, surf, search_key);		
	}

	@Override
	public List<Hotel> selectBySubject(String surf) {
		return hotelMapper.selectBySubject(surf);
	}

	@Override
	public List<Hotel> selectByContent(String surf) {
		return hotelMapper.selectByContent(surf);
	}

	@Override
	public List<HotelreviewVo> reviewSelect(long hnum) {
		return hotelMapper.select(hnum);
	}

	@Override
	public String findNick(String email) {
		return hotelMapper.selectNick(email);
	}
}
