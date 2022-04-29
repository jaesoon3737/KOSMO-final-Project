package soo.md.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandmarkreviewVo {
	private long lnum;
	private long lrnum;
	private String writer;
	private Date rdate;
	private String content;
}
