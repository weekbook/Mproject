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
						<tr>
							<td><c:out value="${board.bno }"></c:out></td>
							<td><a href="${board.bno }" class="move"><c:out
										value="${board.title }"></c:out></a></td>
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
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
					<button class="btn btn-warning" id="searchBtn">검색</button>
				</div>
			</div>

			<br>
			<div>
				<ul class="pagination justify-content-center">
					<c:if test="${pageMaker.prev }">
						<li class="page-item previous"><a
							href="${pageMaker.startPage-1 }" class="page-link">Prev</a></li>
					</c:if>
					<c:forEach var="num" begin="${pageMaker.startPage }"
						end="${pageMaker.endPage }">
						<li class='page-item ${pageMaker.cri.pageNum==num?"active":"" }'>
							<a href="${num }" class="page-link">${num }</a>
					</c:forEach>
					<c:if test="${pageMaker.next }">
						<li class="page-item next"><a href="${pageMaker.endPage+1 }"
							class="page-link">Next</a></li>
					</c:if>
				</ul>

				<form id="actionForm" action="/board/list" method="get">
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }"> 
					<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
					<input type="hidden" name="keyword" value="${pageMaker.cri.keyword }">
					<input type="hidden" name="type" value="${pageMaker.cri.type }">
				</form>
			</div>


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
			"order" : [ [ 0, "desc" ]], // 정렬 0컬럼의 내림차순으로
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
			if(result === ''){
				// ==는 값만 비교, === 은 값과 형식도 비교
				return;
			}
			if($.isNumeric(result)){
				if(parseInt(result) > 0) {
					$(".modal-body").html("게시글 " + parseInt(result) + "번이 등록");
					// 표시할 내용 만들기
				}
			} else{
				$(".modal-body").html(result);
			}
			
			$("#myModal").modal("show"); //모달창 표시
		}
		
		var actionForm = $("#actionForm");
		
		$(".page-item a").on("click", function(e){
			e.preventDefault();
			
			actionForm.find("input[name='pageNum']").val($(this).attr("href"));
			
			actionForm.attr("action", "/board/list");
			actionForm.submit();
		});
		
		$(".move").on("click", function(e){
			e.preventDefault();
			
			var bno = actionForm.find("input[name='bno']").val();
			if(bno != ''){
				actionForm.find("input[name='bno']").remove();
			}
			
			actionForm.append("<input type='hidden' name='bno' "
					+"value='"+$(this).attr("href")+"'>");
			actionForm.attr("action","/board/get");
			actionForm.submit();
		});
		
		$(".search_area button").on("click", function(e){
			e.preventDefault();
			
			var type = $(".search_area select").val();
			var keyword = $(".search_area input[name='keyword']").val();
			
			var sKey = '<c:out value="${pageMaker.cri.keyword}"/>';
			
			if(!type){
				alert("타입을 입력하세요");
				return false;
			}
			if(!keyword){
				alert("키워드를 입력하세요");
				return false;
			}
			if(sKey != keyword){
				actionForm.find("input[name='pageNum']").val(1);
			}
			
			actionForm.find("input[name='type']").val(type);
			actionForm.find("input[name='keyword']").val(keyword);
			actionForm.submit();
		})
	});
</script>


<%@ include file="../includes/footer.jsp"%>

