<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户登录</title>
</head>
<body>

<s:form action="login" method="post">
	<s:textfield name="username" key="login.user.name.label"/>
	<s:password name="password" key="login.user.password.label"/>
    <jsp:include page="/fragment/validation_code.inc" />
    <s:component template="submit_reset_template.jsp">
      <s:param name="submit">
        <s:text name="login.submit" />
      </s:param>
      <s:param name="reset">
        <s:text name="login.reset" />
      </s:param>
    </s:component>
</s:form>
<%--
 <form action="j_security_check" method="post">
	<s:textfield name="j_username" key="login.user.name.label"/>
	<s:password name="j_password" key="login.user.password.label"/>
    <jsp:include page="/fragment/validation_code.inc" />
    <s:component template="submit_reset_template.jsp">
      <s:param name="submit">
        <s:text name="login.submit" />
      </s:param>
      <s:param name="reset">
        <s:text name="login.reset" />
      </s:param>
    </s:component>
</form>
 --%>
<a href="user/register.jsp"><s:text name="register.link"></s:text></a>
</body>
</html>