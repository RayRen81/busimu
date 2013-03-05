<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/fragment/common_header.inc" %>
<title><s:text name="course.create.page.title" /></title>
<title></title>
</head>
<body>
<jsp:include page="/fragment/menu.inc"></jsp:include>

<s:form action="/user/createCourse" method="post">
  <s:actionerror/>
  <s:textfield name="title" key="course.create.form.input.title" />
  <s:textfield name="numOfMarket" key="course.create.form.input.numOfMarket" />
  <s:textfield name="numOfCorpPerMarket" key="course.create.form.input.numOfCorpPerMarket" />
  <s:textfield name="numOfRound" key="course.create.form.input.numOfRound" />
  <s:component template="submit_reset_template.jsp">
    <s:param name="submit" value="%{getText('course.create.form.input.submit')}"/>
    <s:param name="reset" value="%{getText('course.create.form.input.reset')}"/>
  </s:component>
</s:form>

</body>
</html>