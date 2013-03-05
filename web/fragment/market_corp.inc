<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div><s:text name="market.manage.corp.title" /></div>
<table>
<c:forEach var="corp" items="${market.corporations}" varStatus="index">
<c:if test="${index.count%2 == 1}">
	<tr>
</c:if>
<td>
<div class="student-list" style="background-color: ${corp.htmlColor }">
<div>
	<table>
		<tr>
			<td>
				<form method="post" action="<c:url value='/user/editCorp'/>">
					<input type="hidden" name="campaignId" value="${param.campaignId }" />
					<input type="hidden" name="marketId" value="${param.marketId }" />
					<input type="hidden" name="corpId" value="${corp.id}" />
					<table>
						<tr><td><s:fielderror /></td></tr>
						<tr>
							<c:choose>
							<c:when test="${(user.type == 'TEACHER' && campaign.status == 'PREPARE') || campaign.status == 'OPEN'}">
							<td><input name="corpName" value="${corp.name }" /></td>
							<td><s:submit name="submit" key="corp.edit.input.submit" theme="simple"/></td>
							</c:when>
							<c:otherwise>
							<td><c:out value="${corp.name }" /></td>
							</c:otherwise>
							</c:choose>
						</tr>
					</table>
				</form>
			</td>
			<c:if test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
			<td>
				<form method="post" action="<c:url value='/user/deleteCorp'/>">
					<input type="hidden" name="campaignId" value="${param.campaignId }" />
					<input type="hidden" name="marketId" value="${param.marketId }" />
					<input type="hidden" name="corpId" value="${corp.id}" />
					<s:submit name="submit" key="corp.delete.input.submit" theme="simple"/>
				</form>
			</td>
			</c:if>
		</tr>
	</table>
</div>
<hr size="1"/>
<br/>
<c:forEach var="student" items="${corp.team.users}">
<div>
<form action="<c:url value='/user/unassignStudent' />" method="post">
<label>${student.name }</label>
<input type="hidden" name="studentId" value="${student.id}"/>
<input type="hidden" name="teamId" value="${corp.team.id }" />
<input type="hidden" name="campaignId" value="${param.campaignId }" />
<input type="hidden" name="marketId" value="${param.marketId }" />
<c:if test="${user.type == 'TEACHER' || (user.id == student.id && campaign.status == 'OPEN')}">
	<s:submit name="submit" key="market.manage.unassign.form.input.submit" theme="simple"/>
</c:if>
</form>
</div>
<div></div>
</c:forEach>
</div>
</td>
<c:if test="${index.count%2 == 0 || index.last == true}">
	</tr>
</c:if>
</c:forEach>
</table>
