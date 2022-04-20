package kr.icia.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	// Criteria : 페이징  처리 기준을 갖는 밸류 오브젝트
	private int pageNum; // 현재 페이지 번호.
	private int amount; // 페이지당 게시물수
	private String keyword; // 검색 키워드
	
	public Criteria() {
		this(1,10); // 아래쪽 전달값 2개 생성자 호출.
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
}
