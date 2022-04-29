package kr.icia.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	// Criteria : ����¡ ó�� ������ ���� ��� ������Ʈ
	private int pageNum; // ���� ������ ��ȣ.
	private int amount; // �������� �Խù���
	private String keyword; // �˻� Ű����
	private String type; // �˻� Ÿ��
	private String[] typeArr;

	public Criteria() {
		this(1, 10); // �Ʒ��� ���ް� 2�� ������ ȣ��.
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public void setType(String type) {
		this.type = type;
		this.typeArr = type.split("");
	}

	public String[] getTypeArr() {
		// �˻� Ÿ�� �迭 ��������.
		return type == null ? new String[] {} : type.split("");
		// �˻�Ÿ���� ���̶�� ���ִ� ���ڿ� �迭�� �����,
		// �׷��� �ʴٸ�, �˻�Ÿ���� �ѱ��ھ� �߶� ���ڿ� �迭�� ����.
		// t(����), w(�ۼ���), c(����)
	}

	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("").queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount()).queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());
		return builder.toUriString();
		// ������ get ������� �����ϴ� page,amount,type,keyword
		// ��, ���� ������, �������� �Խù���, �˻�Ÿ��, �˻���
		// �� �ּ�â�� get ������� �ٿ��� ���´µ�,
		// ������ ���� ȣ���ؼ� ó���ϴ� ���� �ƴ϶�,
		// getListLink �޼ҵ�� �Ѳ����� ó���ϵ��� ����.
	}
}
