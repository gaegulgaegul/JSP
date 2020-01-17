<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/layout/header.jsp">
	<jsp:param name = "title" value = "Board List" />
</jsp:include>



<c:choose>
	<c:when test = "${ requestScope.list == null}"> <%-- 게시글이 하나도 없을 경우 --%>
		<H2>게시글이 없습니다.</H2>
	</c:when> 
	<c:otherwise> <%-- 게시글이 한 개라도 있을 경우 --%>
		<caption><h2>게시판</h2></caption>
		<table border = "1" width = "100%">
			<tr align = "center">
				<th>글번호</th>
				<th width="47%">글 제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>등록시간</th>
			</tr>
			<c:forEach var = "dto" items = "${ requestScope.list }">
			<tr align = "center">
				<th>${dto.num }</th>
				<th width="47%" align = "left">${dto.title }</th>
				<th>${dto.nickname }</th>
				<th>${dto.hit }</th>
				<th>${dto.regdate }</th>
			</tr>	
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>
<c:if test = "${sessionScope.currentId != null }"> <%-- 현재 로그인한 상태인 경우 (글쓰기 버튼 추가) --%>
	<div align = "center">
		<input type = "Button" value = "글쓰기" onclick = "location.href='BoardWriteForm.do'">
	</div>
</c:if>
<jsp:include page="/layout/footer.jsp" />