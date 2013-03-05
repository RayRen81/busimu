<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员首页</title>
</head>
<body>
<form action="generateLicenses" method="post">
<table>
	<tr>
		<td>认证码类型</td>
		<td>教师:&nbsp<input name="type" value="teacher" type="radio" /></td>
		<td>学生:&nbsp<input name="type" value="student" type="radio" /></td>
	</tr>
	<tr>
		<td>认证码数量</td>
		<td><input name="number" type="text" /></td>
	</tr>
	<tr>
		<td><input name="submit" type="submit" value="生成"></td>
	</tr>
</table>
</form>
</body>
</html>