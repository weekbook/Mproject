<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- jstl core 쓸때 태그에 c 로 표시. -->
<%@ include file="../includes/header.jsp"%>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">글 수정</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-body">
				<form role="form" id="modifyForm" action="/board/modify" method="post">
				<input type="hidden" name="bno" value="${board.bno }"/>
				<input type="hidden" name="pageNum" value="${cri.pageNum }"/>
				<input type="hidden" name="amount" value="${cri.amount }"/>
					<div class="form-group">
						<label>제목</label> <input class="form-control" name="title"
							value='<c:out value="${board.title }"/>'>
					</div>
					<div class="form-group">
						<label>내용</label>
						<textarea rows="3" class="form-control" name="content"><c:out value="${board.content }" /></textarea>
					</div>
					<div class="form-group">
						<label>작성자</label> <input class="form-control" name="writer"
							value='<c:out value="${board.writer }"/> '>
					</div>
					<button type="submit" data-oper='modify' class="btn btn-success">수정</button>
					<button type="submit" data-oper='remove' class="btn btn-danger">삭제</button>
					<button type="submit" data-oper='list' class="btn btn-info">목록</button>
				</form>
			</div>
		</div>
	</div>
</div>
<%@ include file="../includes/footer.jsp"%>


<script>
	$(document).ready(function() {
		/* 문서가 준비 됐다면, 아래 함수 수행. */
		var formObj = $("#modifyForm");/* 문서중 form 요소를 찾아서 변수에 할당. */
		
		$('button').on("click", function(e) {
			/* 버튼이 클릭된다면 아래 함수 수행, e라는 이벤트 객체를
			 전달하면서 */
			e.preventDefault();/* 기본 이벤트 동작 막기. */
			var operation = $(this).data("oper");
			/* 버튼에서 oper 속성 읽어서 변수에 할당. */
			console.log(operation);
			/* 브라우저 로그로 oper값 출력. */
			if (operation === 'remove') {
				formObj.attr("action", "/board/remove");
				/* form에 액션 속성을 변경. */
			} else if (operation === 'list') {
				self.location = "/board/list";
				return;
			}
			formObj.submit();
			/* 위의 조건이 아니라면 수정 처리. */
		});
	});
</script>