package jejufriends.member.repository;

import org.springframework.stereotype.Repository;

import jejufriends.member.domain.TabooWord;

@Repository
public interface TabooRepository {
	void insertTaboo(TabooWord tabooword);
}
