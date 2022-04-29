package jejufriends.course.make.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SaveCourseContent {
	// 나의 일정에 들어있는 컨텐츠들 받는 VO
	private String lodgment;
	private ArrayList<String> contentList;
	public SaveCourseContent(String lodgment, ArrayList<String> contentList) {
		this.lodgment = lodgment;
		this.contentList = contentList;
	}
	
}
