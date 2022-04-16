package jejufriends.member.contol;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jejufriends.member.domain.Faq;
import jejufriends.member.domain.FaqListResult;
import jejufriends.member.domain.FaqSearchListResult;
import jejufriends.member.service.FaqService;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Controller
@RequestMapping("/jejufriends/faq")//�젣二쇳봽�젋利덈줈 �떆�옉�븯�뒗 �젅��寃쎈줈
public class FaqController {
	@Autowired
	private FaqService faqService;

	@GetMapping("/list.do")
	public ModelAndView list(HttpServletRequest request, HttpSession session) {

		session.setAttribute("email", "admin2@gmail.com");


		String cpStr = request.getParameter("cp");
		String psStr = request.getParameter("ps");
		int cp = 1;
		if (cpStr == null) {
			Object cpObj = session.getAttribute("cp");
			if (cpObj != null) {
				cp = (Integer) cpObj;
			}
		} else {
			cpStr = cpStr.trim();
			cp = Integer.parseInt(cpStr);
		}
		session.setAttribute("cp", cp);

		int ps = 10; 
		if (psStr == null) {
			Object psObj = session.getAttribute("ps");
			if (psObj != null) {
				ps = (Integer) psObj;
			}
		} else {
			psStr = psStr.trim();
			int psParam = Integer.parseInt(psStr);

			Object psObj = session.getAttribute("ps");
			if (psObj != null) {
				int psSession = (Integer) psObj;
				if (psSession != psParam) {
					cp = 1;
					session.setAttribute("cp", cp);
				}
			} else {
				if (ps != psParam) {
					cp = 1;
					session.setAttribute("cp", cp);
				}
			}
			ps = psParam;
		}
		session.setAttribute("ps", ps);

		FaqListResult listResult = faqService.getFaqListResult(cp, ps);
		ModelAndView mv = new ModelAndView("faq/list", "listResult", listResult);// 3踰덉㎏�뙆�씪誘명꽣listResult�씪�뒗 �씠由꾩쓽 �뜲�씠�꽣�뿉�뒗
																					// FaqListResult.java�쓽 蹂��닔�뱾�씠 紐⑤몢 梨꾩썙吏�
																					// �뜲�씠�꽣媛� �뱾�뼱媛��엳�떎.
		// ModelAndView mv = new ModelAndView("faq/list_bak", "listResult", listResult);

		if (listResult.getList().size() == 0) {
			if (cp > 1)
				return new ModelAndView("redirect:list.do?cp=" + (cp - 1));// redirect�뒗 �뜲�씠�꽣�뾾�씠 �럹�씠吏� �씠�룞留� �븿//留뚯빟 25媛쒖쓽 由ъ뒪�듃媛� �엳�쓣
																			// �븣(�쟾泥댄럹�씠吏��뒗3�씤 �긽�깭) �떎�쓬 �솕�궡�몴瑜� �늻瑜대㈃ 遺덈윭�삱 由ъ뒪�듃媛� �꼸�씠�땲 4
																			// - 1�빐�꽌 �떎�떆 3�쑝濡� 媛�寃� �븿
			else
				return new ModelAndView("faq/list", "listResult", null);
		} else {
			return mv;// �씠 mv瑜� �샇異쒗븳 怨녹� /faq/list.do �떎.�뜲�씠�꽣瑜� 媛�吏�怨� 洹몃━濡� 媛꾨떎. 洹몃━濡쒕뒗 faq/list(.jsp)瑜� �쓣�슦�씪�뒗 怨녹씠�떎.
		}
	}
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN"})
	@GetMapping("/write.do") // list.jsp�뿉�꽌 遺�瑜� �븣�뒗 �깭洹� �븞�븷 method媛� �뾾�뼱�꽌 �뵒�뤃�듃�씤 Get�쑝濡� 媛꾨떎.
	public String write() {
		return "faq/write";
	}
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN"})
	@PostMapping("/write.do") // write.jsp�뿉�꽌 遺�瑜� �븣�뒗 �깭洹� �븞�븷 method媛� post�씪�꽌 �뿬湲곕줈 �삩�떎.
	public String write(Faq faq) {
		faqService.write(faq);
		return "redirect:list.do?cp=1";
	}

	@GetMapping("/content.do")
	public ModelAndView content(long seq) {
		Faq faq = faqService.getFaq(seq);
		ModelAndView mv = new ModelAndView("faq/content", "faq", faq);
		return mv;
	}
	@Secured({"ROLE_ADMIN" , "ROLE_SUPERADMIN"})	
	@GetMapping("/update.do")
	public ModelAndView update(long seq) {
		Faq faq = faqService.getFaq(seq);
		ModelAndView mv = new ModelAndView("faq/update", "faq", faq);
		return mv;
	}

	@PostMapping("/update.do")
	public String update(Faq faq) {
		faqService.edit(faq);
		return "redirect:content.do?seq=" + faq.getSeq();
	}

	@GetMapping("/del.do")
	public String delete(long seq) {
		faqService.remove(seq);
		return "redirect:list.do?cp=1";
	}

	@PostMapping("autoData.json")
	public @ResponseBody List<Faq> getFaqList(String surf, String search_key) {// subject�뒗 寃��깋李쎌뿉 �엯�젰�븳 �뀓�뒪�듃, search_key�뒗
																				// '�젣紐�'�씤吏� '�궡�슜'�씤吏�.
		// faq/autoData.json?subject=�궡&search_key=subject
		surf = surf.toLowerCase();
		List<Faq> list;
		if ("subject".equals(search_key)) {
			list = faqService.selectBySubject(surf);
		} else {
			list = faqService.selectByContent(surf);
		}
		return list;
	}

	@GetMapping("/search.do")
	public ModelAndView search(HttpServletRequest request, HttpSession session) {
		String cpStr = request.getParameter("cp");
		String psStr = request.getParameter("ps");
		String surf = request.getParameter("surf");
		String search_key = request.getParameter("search_key");
		// faq/search.do?search_key=subject&surf=a;
		int cp = 1;
		if (cpStr == null) {
			Object cpObj = session.getAttribute("cp");
			if (cpObj != null) {
				cp = (Integer) cpObj;
			}
		} else {
			cpStr = cpStr.trim();
			cp = Integer.parseInt(cpStr);
		}
		session.setAttribute("cp", cp);

		int ps = 10;
		if (psStr == null) {
			Object psObj = session.getAttribute("ps");
			if (psObj != null) {
				ps = (Integer) psObj;
			}
		} else {
			psStr = psStr.trim();
			int psParam = Integer.parseInt(psStr);

			Object psObj = session.getAttribute("ps");
			if (psObj != null) {
				int psSession = (Integer) psObj;
				if (psSession != psParam) {
					cp = 1;
					session.setAttribute("cp", cp);
				}
			} else {
				if (ps != psParam) {
					cp = 1;
					session.setAttribute("cp", cp);
				}
			}
			ps = psParam;
		}
		session.setAttribute("ps", ps);
		FaqSearchListResult listResult = faqService.getFaqSearchListResult(cp, ps, surf, search_key);
		ModelAndView mv = new ModelAndView("faq/searchList", "listResult", listResult);

		if (listResult.getList().size() == 0) {
			if (cp > 1) {
				return new ModelAndView("redirect:search.do?cp=" + (cp - 1));
			} else {
				return new ModelAndView("faq/searchList", "listResult", null);
			}
		} else {
			return mv;
		}
	}

	@GetMapping("/kakao.do")//移댁뭅�삤梨꾪똿�쓣 �뀒�뒪�듃�븯湲� �쐞�빐 留뚮뱾�뿀�쓬 url(jejufriends/faq/kakao.do)�쓣 吏곸젒 �엯�젰�빐�꽌 諛섑솚媛믪쓣 �솗�씤�븳�떎.
	public String kakao() {
		return "faq/kakaoTalk";
	}
}