package jejufriends.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jejufriends.community.domain.Community;
import jejufriends.community.domain.CommunitySearch;
import jejufriends.community.repository.CommunityRepository;

@Service
public class CommunityServiceImpl implements CommunityService{
	private final CommunityRepository communityRepository;
	
	@Autowired
	public CommunityServiceImpl(CommunityRepository communityRepository) {
		this.communityRepository = communityRepository;
	}
	
	@Override
	public List<Community> communityListAll() {
		return communityRepository.selectListAll();
	}

	@Override
	public void communityUpload(Community community) {
		communityRepository.insertCommunity(community);
	}

	@Override
	public String findNick(String email) {
		return communityRepository.selectNick(email);
	}

	@Override
	public Community findContent(long comnum) {
		return communityRepository.selectContent(comnum);
	}

	@Override
	public void removeContent(long comnum) {
		communityRepository.deleteContent(comnum);
	}

	@Override
	public void updateContent(Community community) {
		communityRepository.updateContent(community);
	}

	@Override
	public List<Community> searchComminity(CommunitySearch communitySearch) {
		return communityRepository.selectSearch(communitySearch);
	}

	@Override
	public Long checkViews(long comnum) {
		return communityRepository.selectViews(comnum);
	}

	@Override
	public void updateViews(Community community) {
		communityRepository.updateViews(community);
	}

}
