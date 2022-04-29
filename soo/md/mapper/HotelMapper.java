package soo.md.mapper;

import java.util.List;

import soo.md.domain.Hotel;
import soo.md.domain.HotelVo;
import soo.md.domain.HotelreviewVo;

public interface HotelMapper {

	List<Hotel> selectPerPage(HotelVo hotelVo);
	Hotel selectBySeq(long hnum);
	long selectCount();
	Integer searchSelectSubjectCount(String surf);
	Integer searchSelectContentCount(String surf);
	
	List<Hotel> list();
	Hotel content(long hnum);
	void views(long hnum);
	void delete(long hnum);
	void insert(HotelreviewVo hotelreviewVo);
	List<HotelreviewVo> select(long hnum);
	
	List<Hotel> selectSearchSubject(HotelVo hotelVo);
	List<Hotel> selectSearchContent(HotelVo hotelVo);
	List<Hotel> selectBySubject(String surf);
	List<Hotel> selectByContent(String surf);
	
	// 닉네임 조회
	String selectNick(String email);
}
