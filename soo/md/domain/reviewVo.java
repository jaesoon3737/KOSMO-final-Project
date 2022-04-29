package soo.md.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class reviewVo {
	private long fnum;
	private long frnum;
	private String writer;
	private Date rdate;
	private String content;
}
