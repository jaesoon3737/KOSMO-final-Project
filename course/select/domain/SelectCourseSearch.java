package jejufriends.course.select.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SelectCourseSearch {
	private String column;
	private String keyword;
	public SelectCourseSearch(String column, String keyword) {
		this.column = column;
		this.keyword = keyword;
	}
}
