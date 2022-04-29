package jejufriends.community.service;

import java.util.List;

import jejufriends.community.domain.Community;
import jejufriends.community.domain.CommunitySearch;

public interface CommunityService {
	List<Community> communityListAll();
	void communityUpload(Community community);
	String findNick(String email);
	
	// 게시글 핸들링
	Community findContent(long comnum);
	void removeContent(long comnum);
	void updateContent(Community community);
	
	// 검색
	List<Community> searchComminity(CommunitySearch communitySearch);
	
	// 조회수 셋팅
	Long checkViews(long comnum);
	void updateViews(Community community);
}
