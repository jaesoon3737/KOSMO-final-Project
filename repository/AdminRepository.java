package jejufriends.member.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jejufriends.member.domain.AdminUserInfo;
import jejufriends.member.domain.Paging;
import jejufriends.member.domain.PagingKeyword;

@Repository
public interface AdminRepository {
	List<AdminUserInfo> memberfindAll(Paging paging);
	Integer countMember(PagingKeyword pagingKeyword);
}
