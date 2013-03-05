<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册新用户</title>
</head>
<body>
<s:form action="/user/register" method="post">
  <s:textfield name="email" label="邮箱" required="true" />
  <s:textfield name="username" label="用户名" required="true" />
  <s:password name="password" label="密码" required="true"/>
  <s:password name="verifyPassword" label="确认密码" required="true"/>
  <s:component template="submit_reset_template.jsp">
    <s:param name="submit" value="'注册'"/>
    <s:param name="reset" value="'重填'"/>
  </s:component>
</s:form>
</body>
</html>