package jejufriends.community.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import static jejufriends.file.set.FileSet.*;
import jejufriends.community.domain.Community;
import jejufriends.community.domain.CommunitySearch;
import jejufriends.community.service.CommunityService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/jejufriends/community")
@RequiredArgsConstructor
public class CommunityController {
	private final CommunityService communityService;
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("community.do")
	public String community() {
		return "/community/community";
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("community_write.do")
	public String communityWrite() {
		return "/community/community_write";
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("community_upload.do")
	public String communityUpload(@RequestParam MultipartFile file, Community community, Principal principal) throws IOException{
		String email = principal.getName();
		String nick = communityService.findNick(email);
		if(!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
			String uploadName = uuid.toString() + "_" + fileName;
			String fullPath = FILE_DIR + uploadName;
			file.transferTo(new File(fullPath));
			
			community.setEmail(email);
			community.setNick(nick);
			community.setComphoto(uploadName);
			community.setComoriphoto(fileName);
			communityService.communityUpload(community);
		}else {
			community.setEmail(email);
			community.setNick(nick);
			community.setComphoto("");
			community.setComoriphoto("");
			communityService.communityUpload(community);
		}
		return "redirect:/jejufriends/community/community.do";
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("communityList")
	@ResponseBody
	public List<Community> communityList() {
		return communityService.communityListAll();
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("communitySearch")
	@ResponseBody
	public List<Community> communitySearch(CommunitySearch communitySearch) {
		return communityService.searchComminity(communitySearch);
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("communityContent.do")
	public ModelAndView communityContent(long comnum) {
		// 조회수 올리기
		Long views = communityService.checkViews(comnum);
		if(views == null) {
			views = 0L;
		}
		views++;
		Community communityViews = new Community();
		communityViews.setComnum(comnum);
		communityViews.setViews(views);
		communityService.updateViews(communityViews);
		
		// 컨텐츠 VO 가져오기
		Community community = communityService.findContent(comnum);
		return new ModelAndView("/community/community_content", "community", community);
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("removeCommunity.do")
	public String removeCommunity(long comnum) {
		communityService.removeContent(comnum);
		return "redirect:/jejufriends/community/community.do";
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@GetMapping("updateform.do")
	public ModelAndView updateform(long comnum) {
		Community community = communityService.findContent(comnum);
		return new ModelAndView("/community/community_update", "community", community);
	}
	
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN", "ROLE_USER"})
	@PostMapping("community_update.do")
	public String updateCommunity(@RequestParam MultipartFile file, Community community) throws IOException{
		if(!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
			String uploadName = uuid.toString() + "_" + fileName;
			String fullPath = FILE_DIR + uploadName;
			file.transferTo(new File(fullPath));
			
			community.setComphoto(uploadName);
			community.setComoriphoto(fileName);
			communityService.updateContent(community);
		}else {
			Community communityTemp = communityService.findContent(community.getComnum());
			community.setComphoto(communityTemp.getComphoto());
			community.setComoriphoto(communityTemp.getComoriphoto());
			communityService.updateContent(community);
		}
		return "redirect:/jejufriends/community/community.do";
	}
}
