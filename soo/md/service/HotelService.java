package soo.md.service;

import java.util.List;
import soo.md.domain.Hotel;
import soo.md.domain.HotelListResult;
import soo.md.domain.HotelSearchListResult;
import soo.md.domain.HotelreviewVo;

public interface HotelService {
	HotelListResult getHotelListResult(int cp, int ps);
	Hotel getHotel(long hnum);
	void review(HotelreviewVo hotelreviewVo);
	List<HotelreviewVo> reviewSelect(long hnum);
	Hotel content(long hnum);
	
	void remove(long hnum);
	
	HotelSearchListResult getHotelSearchListResult(int cp, int ps, String surf, String search_key);
	List<Hotel> selectBySubject(String surf);
	List<Hotel> selectByContent(String surf);
	
	String findNick(String email);
}
