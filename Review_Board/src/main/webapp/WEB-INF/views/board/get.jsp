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
					<a href="/board/modify?bno=${board.bno }&pageNum=${cri.pageNum}&amount=${cri.amount}"> 수정</a>
				</button>
				<button data-oper="list" class="btn btn-info">
					<a href="/board/list?pageNum=${cri.pageNum }&amount=${cri.amount}&keyword=${cri.keyword}&type=${cri.type}"> 목록</a>
				</button>
				
				<form id="infoForm" action="/board/modify" method="get">
					<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno }"/>'>
					<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum }"/>'>
					<input type="hidden" name="amount" value='<c:out value="${cri.amount }"/>'>
					<input type="hidden" name="keyword" value='<c:out value="${cri.keyword }"/>'>
					<input type="hidden" name="type" value='<c:out value="${cri.type }"/>'>
				</form>
			</div>
		</div>
	</div>
</div>

<br>
<!-- 덧글 목록 시작 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i>덧글
				<button id="addReplyBtn" class="btn btn-primary btn-xs float-right">새 덧글</button> 
			</div>
			<br>
			<div class="panel-body">
				<ul class="chat">
					<li>good</li>
				</ul>
			</div>
			<div class="panel-footer"></div>
		</div>
	</div>
</div>
<!-- 덧글 목록 끝 -->

<!-- 덧글 작성용 모달 시작 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">덧글창</h5>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>덧글</label><input class="form-control" name="reply"
						value="새 덧글">
				</div>
				<div class="form-group">
					<label>작성자</label><input class="form-control" name="replyer"
						value="replyer">
				</div>
				<div class="form-group">
					<label>덧글 작성일</label><input class="form-control" name="replyDate"
						value="">
				</div>
			</div>
			<div class="modal-footer">
				<button id="modalModBtn" type="button" class="btn btnwarning">수정</button>
				<button id="modalRemoveBtn" type="button" class="btn btndanger">삭제</button>
				<button id="modalRegisterBtn" type="button" class="btn btnprimary">등록</button>
				<button id="modalCloseBtn" type="button" class="btn btndefault">닫기</button>
			</div>
		</div>
	</div>
</div>
<!-- 덧글 작성 모달 끝 -->

<script type="text/javascript" src="/resources/js/reply.js"></script>
<script>
	$(document).ready(function() {
		/* console.log(replyServie); */
		
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
		 
		 
		var bnoValue = '<c:out value="${board.bno}"/>';
		
		/* 기존에 테스트하던 코드느 주석처리 */
		/* replyService.add({
			reply : "js test",
			replyer : "tester",
			bno : bnoValue
		}, function(result){
			alert("result: " + result);
		}); */
		 // 게시글을 읽을때 자동으로 댓글 1개 등록.
		 
		var modal = $("#myModal");
		// 덧글 용 모달.
		var modalInputReplydate = modal.find("input[name='replyDate']");
		// 덧글 작성일 항목.
		var modalRegisterBtn = $("#modalRegisterBtn");
		// 모달에서 표시되는 덧글쓰기 버튼.
		var modalInputReply = modal.find("input[name='reply']");
		// 덧글 내용
		var modalInputReplyer = modal.find("input[name='replyer']"); 
		// 덧글작성자
		
		// 덧글 입력 모달창 보이기
		$("#addReplyBtn").on("click", function(e){
			// 덧글 쓰기 버튼을 클릭한다면,
			modal.find("input").val("");
			// 모달의 모든 입력창을 초기화
			modalInputReplydate.closest("div").hide();
			// closest : 선택 요소와 가장 가까운 요소를 지정.
			// 즉, modalInputReplyDate 요소의 가장 가까운 div를 찾아서 숨김.(날짜창 숨김)
			modal.find("button[id != 'modalCloseBtn']").hide();
			// 모달창에 버튼이 4개 인데, 닫기 버튼을 제외하고 숨기기.
			modalRegisterBtn.show(); // 등록 버튼은 보여라.
			$("#myModal").modal("show"); // 모달 표시.
		});
		
		// 모달창 닫기
		$("#modalCloseBtn").on("click", function(e){
			modal.modal("hide");
			// 모달 닫기 라는 버튼을 클릭한다면 모달창을 숨김.
		});
		
		// 덧글 쓰기
		modalRegisterBtn.on("click", function(e){
			// 덧글 등록 버튼을 눌렀다면,
			var reply = {
					reply : modalInputReply.val(),
					replyer : modalInputReplyer.val(),
					bno : bnoValue
			}; // ajax로 전달할 reply 객체 선언 및 할당.
			replyService.add(reply, function(result){
				alert(result);
				// ajax 처리후 결과 리턴.
				modal.find("input").val("");
				// 모달창 초기화
				modal.modal("hide"); // 모달창 숨기기
			});
		});
		
		// 덧글 목록 보이기.
		replyService.getList({
			bno : bnoValue,
			page : 1
		}, function(list){
			for(var i=0, len = list.length || 0; i<len; i++){
				console.log(list[i]);
			}
		});
		
	}); // end_document_ready
		
</script>

<%@ include file="../includes/footer.jsp"%>