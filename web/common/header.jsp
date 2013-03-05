<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
	<img src="<c:url value="/icon/busimu_logo.png"/>" alt="Busimu Corp" />
	<span style="position: fixed; right: 20px; ">
		<span style="position: relative; top: 10px; ">
			<span>
				<c:choose>
					<c:when test="${'TEACHER' == user.type}">
						<c:out value="欢迎您, ${user.name }老师" />
					</c:when>
					<c:when test="${'STUDENT' == user.type}">
						<c:out value="欢迎您, ${user.name }同学  "/>
					</c:when>
				</c:choose>
			</span>
			<c:if test="${not empty user}">
				<span>|</span>
				<span><a href="logout">退出</a></span>
			</c:if>
		</span>
	</span>
</body>
</html>