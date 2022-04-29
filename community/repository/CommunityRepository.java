package jejufriends.community.repository;

import java.util.List;

import jejufriends.community.domain.Community;
import jejufriends.community.domain.CommunitySearch;

public interface CommunityRepository {
	List<Community> selectListAll();
	void insertCommunity(Community community);
	String selectNick(String email);
	
	// 게시글 핸들링
	Community selectContent(long comnum);
	void deleteContent(long comnum);
	void updateContent(Community community);
	
	// 검색
	List<Community> selectSearch(CommunitySearch CommunitySearch);
	
	// 조회수 셋팅
	Long selectViews(long comnum);
	void updateViews(Community community);
}
