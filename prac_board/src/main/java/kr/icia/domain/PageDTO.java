package kr.icia.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	// DTO : data transfer object
	private int startPage; // ����¡ ����, ����ȣ�� 10����  ǥ���Ѵٸ�, 1 Ȥ�� 11 Ȥ�� 21...
	private int endPage; // 1��������� �����ϸ� endPage�� 10
	private boolean prev, next; // ����, ���� ��ư. ��쿡 ���� �����ֱ�.
	private int total; // �� �Խù� ��
	private Criteria cri; // ������������ �������� �Խù� ��
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		this.endPage
		= (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		// 1��������� �����ϸ� endPage�� 10
		this.startPage = this.endPage - 9; // 1	
		int realEnd
		= (int) (Math.ceil((total * 1.0)/ cri.getAmount()));
		// �ѰԽù��� 20����� �����ϸ� realEnd = 2
		// �������� ������ �Խù� ���� 10���� ����
		
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
