<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- jstl core 쓸때 태그로 c로 표시 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- jstl fmt 쓸때 위와 같음, fmt : fomatter 형식 맞춰서 표시 -->
<%@ include file="../includes/header.jsp"%>

<h1 class="h3 mb-2 text-gray-800">Tables</h1>
<p class="mb-4">
	DataTables is a third party plugin that is used to generate the demo
	table below. For more information about DataTables, please visit the <a
		target="_blank" href="https://datatables.net">official DataTables
		documentation</a>.
</p>

<!-- DataTales Example -->
<div class="card shadow mb-4">

	<div class="card-header py-3" align="right">
		<button id="regBtn" style="color: green;">글쓰기</button>
	</div>

	<div class="card-body">
		<div class="table-responsive">
			<table class="table table-bordered" id="dataTable" width="100%"
				cellspacing="0">
				<thead>
					<tr>
						<th>#번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>수정일</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="board" items="${list }">
					<!-- items : boardVO 객체 여러개의 배열 -->
						<tr>
							<td><c:out value="${board.bno }"></c:out></td>
							<td><a href="${board.bno }" class="move"><c:out
										value="${board.title }"/>
										<!-- JSTL문법 != 을 ne로 표시한것  / == eq -->
										<c:if test="${board.replyCnt ne 0 }">
										<span style="color:red;">[<c:out value="${board.replyCnt}"/>]</span>
										</c:if>
										</a></td>
							<td><c:out value="${board.writer }"></c:out></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.regdate }" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.updateDate }" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div class="search_wrap">
				<div class="search_area">
					<select name="type">
		                <option value="" <c:out value="${pageMaker.cri.type == null?'selected':'' }"/>>--</option>
		                <option value="T" <c:out value="${pageMaker.cri.type eq 'T'?'selected':'' }"/>>제목</option>
		                <option value="C" <c:out value="${pageMaker.cri.type eq 'C'?'selected':'' }"/>>내용</option>
		                <option value="W" <c:out value="${pageMaker.cri.type eq 'W'?'selected':'' }"/>>작성자</option>
		                <option value="TC" <c:out value="${pageMaker.cri.type eq 'TC'?'selected':'' }"/>>제목 + 내용</option>
		                <option value="TW" <c:out value="${pageMaker.cri.type eq 'TW'?'selected':'' }"/>>제목 + 작성자</option>
		                <option value="TCW" <c:out value="${pageMaker.cri.type eq 'TCW'?'selected':'' }"/>>제목 + 내용 + 작성자</option>
	           		 </select>
					<input type="text" name="keyword" value="${pageMaker.cri.keyword }">
					<button class="btn btn-warning" id="searchBtn">Search</button>
				</div>
			</div>		
			
			
			<br>	
			<!-- 쪽번호 시작 -->
			 <div>
			 	<ul class="pagination justify-content-center">
			 		<!-- 이전페이지 버튼 -->
			 		<c:if test="${pageMaker.prev }">
			 			<li class="page-item previous">
			 				<a href="${pageMaker.startPage-1 }" class="page-link">Prev</a>
			 			</li>
			 		</c:if>
			 		<c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
			 		<!-- 게시물 목록에서 배열을 이용한 것과 같이
			 		begin은 배열의 시작값, end=배열의 끝값으로 순환 처리.-->
			 			<li class='page-item ${pageMaker.cri.pageNum==num?"active":"" }'>
			 			<!-- pageMaker객체의 cri객체의 pageNum이 현재 num과 같다면, active 출력, 아니면 공백 출력.
			 			active는 스타일 강조처리가 부트스트랩에 이미 설정되어 있음.-->
			 				<a href="${num }" class="page-link">${num }</a>
			 			</li>			 		
			 		</c:forEach>
			 		
			 		<!-- 다음페이지 버튼 -->
			 		<c:if test="${pageMaker.next }">
			 			<li class="page-item next">
			 				<a href="${pageMaker.endPage+1 }" class="page-link">Next</a>
			 			</li>
			 		</c:if>
			 	</ul>
			 	
			 	<!-- 페이지 이동할 때 필요정보인 pageNum과 amount정보를 전송하기  위해 작성 -->
			 	<!-- value값으로 현재 페이지의 정보가 저장되어있음 -->
			 	
			 	<form id="actionForm" action="/board/list" method="get">
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
					<input type="hidden" name="keyword" value="${pageMaker.cri.keyword }">
					<input type="hidden" name="type" value="${pageMaker.cri.type }">
				</form>
			 </div>
			
			<!-- 쪽번호 끝 -->
			
			
		</div>
	</div>
</div>

<!-- 알림창 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header"></div>
			<div class="modal-body"></div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>




<script>
	$(document).ready(function() {
		console.log("목록 페이지0414");
		$('#dataTable').DataTable({
			"order" : [ [ 0, "desc" ] ], // 정렬 0컬럼의 내림차순으로
			"paging" : false, // 페이징 표시 안함.
			"bFilter" : false, // 검색창 표시 안함.
			"info" : false
		//안내창 표시 안함
		});

		$("#regBtn").on("click", function() {
			self.location = "/board/register";
			/* 아이디 regBtn 을 클릭한다면
			현재창의 url를 쓰기로 변경 */
		});

		var result = '<c:out value="${result}"/>';
		// 자바스크립트 형추론 이용.
		checkModal(result);
		// 게시판 번호를 매개변수로 전달하면서 checkModal 평션 호출

		function checkModal(result) {
			if (result === '') {
				// ==는 값만 비교, === 은 값과 형식도 비교
				return;
			}

			if ($.isNumeric(result)) { // 전달된 값이 숫자인가
				if (parseInt(result) > 0) {
					$(".modal-body").html("게시글 " + parseInt(result) + "번 등록");
				}
			} else {
				$(".modal-body").html(result);
				// 수정과 삭제시에는 success 라는 문자열이 전달 되므로, 숫자화 할 수 없음.
				// 표시할 내용 만들기
			}
			$("#myModal").modal("show"); //모달창 표시
		}
		
		
	});
	
	/* 페이지 클릭시 페이지 이동 기능 구현 */
	var actionForm = $("#actionForm");
	
	$(".page-item a").on("click", function(e){
		e.preventDefault(); // 기존 이벤트 동작 방지
		
		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		// 2페이지를 선택한다면, localhosr:8091/board/2 */
		// 액션폼 인폿태그에 페이지넘 값을 찾아서, href로 받은 값으로 대체함.
		
		actionForm.attr("action", "/board/list");
		actionForm.submit();
		
		/* 처음 액션폼에는 히든(숨김(으로 쪽번호1과 쪽당게시물10이 할당되어 있음.)) 
		아래쪽 쪽번호 2를 선택한다면, 액션폼의 쪽번호로를 2로 변경하고, 폼 서브밋.
		리스트 컨트롤러가 받아서 Criteria 객체를 초기화 하고, 그것을 통해서
		게시물(list)과 쪽번호 속성(PageMaker)들을 초기화 함. 
		*/
	});
	
	// /board/글번호	
	$(".move").on("click",function(e) {
		e.preventDefault();
		
		var bno = actionForm.find("input[name='bno']").val();
		// 게시물 읽고 뒤로가기 버튼했을때 같은 게시물에 들어가지는 오류 해결
		// bno가 이미 있다면, 지운다는 뜻
		if(bno != ''){
			actionForm.find("input[name='bno']").remove();
		}
		
		actionForm.append("<input type='hidden' name='bno' "
				+"value='"+$(this).attr("href")+"'>");
		// <input type='hidden' name='bno' value='글번호'>
		actionForm.attr("action","/board/get");
		actionForm.submit();
		// http://localhost:8091/board/get?pageNum=10&amount=10&bno=191
	});
	
	
	$(".search_area button").on("click", function(e){
		
		var type = $(".search_area select").val();
		var keyword = $(".search_area input[name='keyword']").val();
		
		var sKey = '<c:out value="${pageMaker.cri.keyword}"/>';
		// Criteria 필드 멤버의 검색어.
		
		console.log("이전 검색어: " + sKey);
		console.log("현재 검색어: " + keyword);
		
		if(!type){
			alert("키워드를 입력하세요");
			return false;
		}
		
		if(!keyword){
			alert("키워드를 입력하세요.");
			return false;
		}
		
		if(sKey != keyword){
			actionForm.find("input[name='pageNum']").val(1);
			// 새로운 검색어라면 1페이지로 이동.
		}
		
		
		actionForm.find("input[name='type']").val(type);
		actionForm.find("input[name='keyword']").val(keyword);
		// 1페이지로 이동하는 구문
		/* actionForm.find("input[name='pageNum']").val(1); */
		actionForm.submit();
	});
</script>


<%@ include file="../includes/footer.jsp"%>
