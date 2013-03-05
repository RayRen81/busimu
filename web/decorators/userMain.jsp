<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<decorator:head />
<title><decorator:title default="Busimu商业模拟系统"></decorator:title></title>
</head>
<body>
<div style="background-image: url('<c:url value="/icon/logo_bg.png"/>'); background-repeat: repeat-x;"><page:apply-decorator name="pageHeader" page="/common/header.jsp"/></div>
<div id="content-panel"><decorator:body /></div>
<div id="footer-panel" style="background-color: rgb(26, 151, 184); text-align: center"><page:apply-decorator name="pageFooter" page="/common/footer.jsp"/></div>
</body>
</html>