package jejufriends.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CommunitySearch {
	private String column;
	private String keyword;
	
	public CommunitySearch(String column, String keyword) {
		this.column = column;
		this.keyword = keyword;
	}
}
