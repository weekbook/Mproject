package kr.icia.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


// @Getter 위치 중요 : 초기화후 호출해야한다. 생략하면 호출하고 초기황 하여 오류 가 발생
@Data
@AllArgsConstructor
@Getter
public class ReplyPageDTO {
	private int replyTotalCnt; // 게시물별 덧글의 총 갯수.
	private List<ReplyVO> list; // 덧글의 목록
}
