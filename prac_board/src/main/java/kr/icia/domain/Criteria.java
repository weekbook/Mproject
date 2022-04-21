package kr.icia.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	// Criteria : ����¡  ó�� ������ ���� ��� ������Ʈ
	private int pageNum; // ���� ������ ��ȣ.
	private int amount; // �������� �Խù���
	private String keyword; // �˻� Ű����
	private String type; // �˻� Ÿ��
	private String[] typeArr;
	
	public Criteria() {
		this(1,10); // �Ʒ��� ���ް� 2�� ������ ȣ��.
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public void setType(String type) {
		this.type = type;
		this.typeArr = type.split("");
	}
}
