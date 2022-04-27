package kr.icia.domain;

import lombok.Data;

@Data
public class AttachFileDTO {

	// ÷������ 1���� ���� ó��.
	// BoardAttachVO �� ��� �Խù��� ÷���������� ó��.
	// ÷������ ������ ��� ����, ÷������ 2�� �����ʹ� ������ Ư�� ������ ����,
	// Ư�� ������ ������ �����ϱ� ���� Ŭ����.
	private String fileName;
	private String uploadPath;
	private String uuid;
	private boolean image;
}
