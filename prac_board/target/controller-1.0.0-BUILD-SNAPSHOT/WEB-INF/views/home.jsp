<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<!-- 
사용자가 웹 브라우저를  이용하여 url에 주소로(get) 서비스 요청,
pom.xml >> web.xml >> root-context.xml >> servlet-context.xml >> controller 에서
매핑되는 메소드 찾기.(home) >> 오늘 날짜와 시간을 생성하여 문자열로 변환하고 model에 serverTime 이라는 이름으로 담아서 표시할 페이지 호출
servlet-context.xml 설정에 따라서
/WEB-INF/views/home.jsp 를 표시하면서
serverTime에 담겨진 날짜와 시간을 함께 표시함.
 -->
</body>
</html>
