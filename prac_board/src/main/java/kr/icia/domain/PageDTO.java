package kr.icia.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	// DTO : data transfer object
	private int startPage; // 페이징 시작, 끝번호를 10개씩  표시한다면, 1 혹은 11 혹은 21...
	private int endPage; // 1페이지라고 가정하면 endPage는 10
	private boolean prev, next; // 이전, 다음 버튼. 경우에 따라서 보여주기.
	private int total; // 총 게시물 수
	private Criteria cri; // 현재페이지와 페이지당 게시물 수
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		this.endPage
		= (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		// 1페이지라고 가정하면 endPage는 10
		this.startPage = this.endPage - 9; // 1	
		int realEnd
		= (int) (Math.ceil((total * 1.0)/ cri.getAmount()));
		// 총게시물이 20개라고 가정하면 realEnd = 2
		// 페이지당 보여줄 게시물 수는 10개로 가정
		
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
