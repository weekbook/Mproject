<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">게시판</h1>
                    <a href="/">홈</a>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                    	<div class="card-header py-3" align="right">
                    		<button id="regBtn" style="color: green;">글쓰기</button>
                    	</div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
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
                                   				<td><a href="/board/get?bno=${board.bno }">
                                   				<c:out value="${board.title }"></c:out>
                                   				</a></td>
                                   				<td><c:out value="${board.writer }"></c:out></td>
                                   				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate }"/></td>
                                   				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate }"/></td>
                                   			</tr>
                                   		</c:forEach>
                                   	</tbody>
                                </table>

                            </div>
                        </div>
                    </div>
                </div>
</body>
</html>

<script>
    $(document).ready(function(){
        $('#dataTable').DataTable({
        			"order" : [[0,"desc"]],
        			"paging" : false,
        			"bFilter" : false,
        			"info" : false
        		});

        $("#regBtn").on("click", function() {
        			self.location = "/board/register";
        		});

        $(".move").on("click", function(e) {
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
    });

</script>
<%@ include file="../includes/footer.jsp" %>