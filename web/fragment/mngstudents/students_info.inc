<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="bf" uri="/busimu-functions" %>

<c:set var="student" value="${bf:getUserById(param.studentId)}"/>
<h3>basic info</h3>
<div id="basic_info">
<table>
	<tr>
		<td>name</td>
		<td></td>
		<td>email</td>
		<td></td>
	</tr>
	<tr>
		<td>market</td>
		<td></td>
		<td>corp</td>
		<td></td>
	</tr>
	<tr>
		<td>campaign status</td>
		<td></td>
		<td></td>
		<td></td>
	</tr>	
</table>
</div>
<br />
<hr size="1"/>
<br />
<div id="login_history">
<h3>login history</h3>
<table border="1" cellpadding="1" cellspacing="0" >
	<thead>
		<tr>
			<td>seq</td>
			<td>login time</td>
			<td>logout time</td>
			<td>duration</td>
		</tr>
	</thead>
	<tbody>
				<c:forEach var="loginHistory" items="${bf:getRealLoginHistory(student, campaign)}" varStatus="index">
						<tr>
							<td>${index.count }</td>
							<td>${loginHistory.loginDate }</td>
							<td>${loginHistory.logoutDate }</td>
							<td>hello</td>
						</tr>
				</c:forEach>
		</tbody>
</table>
</div>