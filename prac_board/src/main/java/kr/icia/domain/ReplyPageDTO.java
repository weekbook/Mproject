package kr.icia.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


// @Getter ��ġ �߿� : �ʱ�ȭ�� ȣ���ؾ��Ѵ�. �����ϸ� ȣ���ϰ� �ʱ�Ȳ �Ͽ� ���� �� �߻�
@Data
@AllArgsConstructor
@Getter
public class ReplyPageDTO {
	private int replyTotalCnt; // �Խù��� ������ �� ����.
	private List<ReplyVO> list; // ������ ���
}
