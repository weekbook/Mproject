<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includes/header.jsp"%>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">글 읽기</h1>
	</div>
</div>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading"></div>
			<div class="panel-body">
				<div class="form-group">
					게시물 번호<input class="form-control" name="bno"
						value='<c:out value="${board.bno }"/>' readonly="readonly">
				</div>
				<div class="form-group">
					제목<input class="form-control" name="title"
						value='<c:out
value="${board.title }"/>' readonly="readonly">
				</div>
				<div class="form-group">
					내용
					<textarea rows="3" class="form-control" name="content"
						readonly="readonly"><c:out value="${board.content }" /></textarea>
				</div>
				<div class="form-group">
					작성자<input class="form-control" name="writer"
						value='<c:out
value="${board.writer }"/>' readonly="readonly">
				</div>
				<button data-oper="modify" class="btn btn-warning">
					<a href="/board/modify?bno=${board.bno }"> 수정</a>
				</button>
				<button data-oper="list" class="btn btn-info">
					<a href="/board/list"> 목록</a>
				</button>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		var formObj = $("#infoForm");

		/* url을 스크립트에서 추가하는 방식 */
		// 클래스로했더니 안되서 id로 했더니 되었더라
		/* 	$("#list_btn").on("click", function(e){
		 form.find("#bno").remove();
		 form.attr("action", "/board/list");
		 form.submit();
		 }); */	

		$("#list_btn").on("click", function(e) {
			e.preventDefault();
			var operation = $(this).data("oper");
			/* 버튼에서 oper 속성 읽어서 변수에 할당 */
			console.log(operation);
			/* 브라우저 로그로  oper값 출력 */
			if (operation === 'list') {
				// self.loacation = "/board/list"
				// return;
				formObj.attr("action", "/board/list")
				formObj.find("bno").remove();
				/* form 에서 아이디 bno 요소를 찾아서 삭제 */
			}
			formObj.submit();
		});

		/* 	$("#modify_btn").on("click", function(e){
		 formObj.attr("action", "/board/modify");
		 formObj.submit();
		 }); */

		$("#modify_btn").on("click", function(e) {
			e.preventDefault();
			var operation = $(this).data("oper");
			console.log(operation);
			if (operation === 'modify') {
				formObj.attr("action", "/board/modify")
			}
			formObj.submit();
		});
		 
	});
		
		