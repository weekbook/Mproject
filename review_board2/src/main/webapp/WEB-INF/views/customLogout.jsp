<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- jstl core 쓸때 태그로 c로 표시 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- jstl fmt 쓸때 위와 같음, fmt : fomatter 형식 맞춰서 표시 -->
<%@ include file="includes/header.jsp"%>


<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<h1>로그아웃 처리</h1>
			<h2>${error }</h2>
			<h2>${logout }</h2>
			<div class="panel-body">
				<form role="form" method="post" action="/customLogout">
					<fieldset>
						<a href="index.html" class="btn btn-lg btn-success btn-block">
							로그아웃</a>
					</fieldset>
					<input type="hidden" name="${_csrf.parameterName }"
						value="${_csrf.token }">
				</form>
			</div>
		</div>
	</div>
</div>

<script>
	$(".btn-success").on("click", function(e){
		e.preventDefault();
		$("form").submit();
	});
</script>

<c:if test="${param.logout != null }">
	<script>
		$(document).ready(function(){
			alert("로그아웃");
		});
	</script>
	<!-- 로그아웃 파라미터 값이 있다면, 로그아웃 안내창 표시 -->
</c:if>


<%@ include file="includes/footer.jsp"%>