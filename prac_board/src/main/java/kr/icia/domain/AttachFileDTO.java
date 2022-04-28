package kr.icia.domain;

import lombok.Data;

@Data
public class AttachFileDTO {

	// 첨부파일 1개에 대한 처리.
	// BoardAttachVO 는 어느 게시물의 첨부파일인지 처리.
	// 첨부파일 정보는 디비에 저장, 첨부파일 2진 데이터는 서버의 특정 폴더에 저장,
	// 특정 폴더에 파일을 저장하기 위한 클래스.
	private String fileName;
	private String uploadPath;
	private String uuid;
	private boolean image;
}
