<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includes/header.jsp"%>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">글읽기</h1>
	</div>
</div>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel -default">
			<div class="panel-body">
				<div class="form-group">
					게시물 번호<input class="form-control" name="bno"
						value='<c:out value="${board.bno }"/>' readonly="readonly">
				</div>
				<div class="form-group">
					<label>제목</label> <input class="form-control" name='title'
						value='<c:out value="${board.title }"/>' readonly="readonly">
				</div>
				<div class="form-group">
					<label>내용</label>
					<textarea class="form-control" rows="3" name='content'
						readonly="readonly"><c:out value="${board.content }" />
               		</textarea>
				</div>
				<div class="form-group">
					<label>작성자</label> <input class="form-control" name="writer"
						value='<c:out value="${board.writer }"/>' readonly="readonly">
				</div>
				<button data-oper="modify" id="modify_btn" class="btn btn-warning">
					<a
						href="/board/modify?bno=${board.bno }pageNum=${cri.pageNum }&amount=${cri.amount }">수정</a>
					<!-- <a id="modify_btn">수정</a> -->
				</button>

				<button data-oper="list" id="list_btn" class="btn btn-info">
					<!-- 방식1 script방식 -->
					<!-- <a href="/board/list" >목록</a> -->
					<!-- 방식2 url방식-->
					<a href="/board/list?pageNum=${cri.pageNum }&amount=${cri.amount }">목록</a>
				</button>

				<!-- 글 읽기 창에서 페이지 정보 처리 시작 -->
				<form id="infoForm" action="/board/modify" method="get">
					<input type="hidden" id="bno" name="bno"
						value='<c:out value="${board.bno }"/>'> <input
						type="hidden" name="pageNum"
						value='<c:out value="${cri.pageNum }"/>'> <input
						type="hidden" name="amount"
						value='<c:out value="${cri.amount }"/>'> <input
						type="hidden" name="keyword"
						value='<c:out value="${cri.keyword }"/>'> <input
						type="hidden" name="type" value='<c:out value="${cri.type }"/>'>
				</form>
				<!-- 폼을 생성해서 게시물번호를 숨김 값으로 전달.
					나중에 현재 페이지 번호, 페이지당 게시물수, 검색어, 검색타입 추가 예정
					 -->

			</div>
		</div>
	</div>
</div>

<!-- 덧글 목록 시작 -->
<br />	
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-defualt">
			<div class="panel-heding">
				<i class="fa fa-comments fa-fw"></i>덧글
				<button id="addReplyBtn" class="btn btn-primary btn-xs float-right">
					새 덧글</button>
			</div>
			<br />
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
<!-- 덧글 작성용 모달 끝 -->

<script type="text/javascript" src="/resources/js/reply.js"></script>
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
		 
		 
		var modalModBtn=$("#modalModBtn");
		var modalRemoveBtn = $("#modalRemoveBtn");

		console.log(replyService);
		var bnoValue = '<c:out value="${board.bno}"/>';

		/* replyService.add({
			reply : "js test",
			replyer : "tester",
			bno : bnoValue
		}, function(result) {
			console.log("result: " + result);
			alert("result: " + result);
		}); */
		// 게시글을 읽을때 자동으로 댓글 1개 등록.
		// replyService.add(replyVO, callback)
		var modal = $("#myModal");
		// 덧글 용 모달.
		var modalInputReplyDate = modal.find("input[name='replyDate']");
		// 덧글 작성일 항목.
		var modalRegisterBtn = $("#modalRegisterBtn");
		// 모달에서 표시되는 덧글쓰기 버튼.
		var modalInputReply = modal.find("input[name='reply']");
		// 덧글 내용
		var modalInputReplyer = modal.find("input[name='replyer']"); //덧글 작성자.
		// 덧글 입력 모달창 보이기.
		$("#addReplyBtn").on("click", function(e) {
			// 덧글 쓰기 버튼을 클릭한다면,
			modal.find("input").val("");
			// 모달의 모든 입력창을 초기화
			modalInputReplyDate.closest("div").hide();
			// closet: 선택 요소와 가장 가까운 요소를 지정.
			// 즉, modalInputReplyDate 요소의 가장 가까운
			// div를 찾아서 숨김. (날짜창 숨김)
			modal.find("button[id != 'modalCloseBtn']").hide();
			// 모달창에 버튼이 4개 인데, 닫기 버튼을 제외하고 숨기기.
			modalRegisterBtn.show(); // 등록 버튼을 보여라.
			$("#myModal").modal("show"); // 모달 표시
		});

		$("#modalCloseBtn").on("click", function(e) {
			modal.modal("hide");
			// 덧글 모달 닫기 라는 버튼을 클릭한다면 모달창을 숨김.
		});

		// 모달창에서 덧글 쓰기
		modalRegisterBtn.on("click", function(e) {
			// 덧글 등록 버튼을 눌렀다면,
			var reply = {
				reply : modalInputReply.val(),
				replyer : modalInputReplyer.val(),
				bno : bnoValue
			}; // ajax로 전달할 reply 객체 선언 및 할당.
			replyService.add(reply, function(result) {
				alert(result);
				// ajax 처리후 결과 리턴.
				modal.find("input").val("");
				// 모달창 초기화
				modal.modal("hide"); // 모달창 숨기기
				
				// 덧글 작성 즉시 목록 갱신용 함수 호출.
				showList(-1);
				// -1 이나 99나 현재는 영향이 없지만 차후 덧글의 페이징 처리에서 -1 사용 예정
				
				
			});
		}); // 덧글 쓰기버튼처리 끝.

		// 덧글 목록 보이기.
		/* replyService.getList({
			bno : bnoValue,
			pgee : 1
		}, function(list) {
			for (var i = 0, len = list.length || 0; i < len; i++) {
				console.log(list[i]);
			}
		}); */
		
		var replyUL = $(".chat");
		
		function showList(page){
			replyService.getList(
					{
						bno : bnoValue,
						page : page || 1
					},
					// 익명함수 : 이름이 없으며 즉시 실행
					function(replyTotalCnt, list) {
						console.log("replyTotalCnt : " + replyTotalCnt);
						
						if(page == -1){
							// 페이지 번호가 음수 값 이라면,
							
							PageNum = Math.ceil(replyTotalCnt / 10.0);
							// 덧글의 마지막 페이지 구하기.
							
							showList(pageNum);
							// 덧글 목록 새로고침(갱신)
							
							return;
						}
						
						var str = "";
						
						if (list ==null || list.length == 0 ){
							replyUL.html("");
							return;
						} // 목록이 없을때 처리 끝.
						
						for (var i = 0, len = list.length || 0; i<len; i++){
							str += "<li class='left ";
							str += "clearfix' data-rno='";
							str += list[i].rno+"'>";
							str += "<div><div class='header' ";
							str += "><strong class='";
							str += "primart-font'>";
							str += list[i].replyer+ "</strong>";
							str += "<small class='float-sm-right '>";
							str += replyService.displayTime(list[i].replyDate)
							+ "</small></div>";
							str += "<p>" + list[i].reply;
							str += "</p></div></li>";
						}
						replyUL.html(str); 
					}); // end
		} // end_showlist
		showList(1);
		// 덧글 목록 표시 끝 

		// 댓글 정보 확인
        $(".chat").on("click", "li", function(e){
            // 클래스 char 을 클릭하는데, 하위 요소가 li라면,
            var rno = $(this).data("rno");
            // 덧글에 포함된 값들 중에서 rno를 추출하여 변수 할당.
            console.log(rno);
            
            replyService.get(rno,function(reply){
                modalInputReply.val(reply.reply);
                modalInputReplyer.val(reply.replyer);
                modalInputReplyDate.val(replyService.displayTime(reply.replyDate))
                        .attr("readonly", "readonly");
                // 댓글 목록의 값들을 모달창에 할당.
                modal.data("rno", reply.rno);
                // 표시되는 모달창에 rno 라는 이름으로 data-rno를 저장.
                modal.find("button[id != 'modalCloseBtn']").hide();
                modalModBtn.show();
                modalRemoveBtn.show();
                // 버튼 보이기 설정.
                $("#myModal").modal("show");
            }); // 끝_덧글 읽기.
        });
		
		// 덧글 수정 처리 시작.
		modalModBtn.on("click", function(e) {
			var reply = {
					rno : modal.data("rno"),
					/* replyer : modalInputReplyer.val(), */
					reply : modalInputReply.val()
			};
			replyService.update(reply, function(result){
				alert(result);
				modal.modal("hide");
				showList(-1);
			});
		}); // 끝 덧글 수정.
		
		// 덧글 삭제 처리.
		modalRemoveBtn.on("click", function(e){
			var rno = modal.data("rno");
			replyService.remove(rno, function(result){
				alert(result);
				modal.modal("hide");
				showList(-1);
			});
		});
		
	});
</script>

<%@ include file="../includes/footer.jsp"%>