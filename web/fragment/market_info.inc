<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
	<tr>
		<td>
			<form action="<c:url value='/user/editMarket' />" method="post">
				<table>
					<tr>
						<td><s:fielderror/></td>
					</tr>
					<tr>
						<td><s:label key="market.edit.input.name" theme="simple"/>: </td>
						<c:choose>
						<c:when test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
						<td><input name="marketName" value="${market.name }"/></td>
						<td><input type="hidden" name="marketId" value="${market.id} "></td>
						<td><input type="hidden" name="campaignId" value="${campaign.campaignId} "></td>
						<td><s:submit name="submit" key="course.edit.input.submit" theme="simple"/></td>
						</c:when>
						<c:otherwise>
						<td><c:out value="${market.name}"/></td>
						</c:otherwise>
						</c:choose>
					</tr>
				</table>
			</form>
		</td>
		<c:if test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
		<td>
			<s:form action="deleteMarket" method="post">
				<input type="hidden" name="marketId" value="${market.id} ">
				<input type="hidden" name="campaignId" value="${campaign.campaignId} ">
				<s:submit name="submit" key="market.delete.input.submit" theme="simple"/>
			</s:form>
		</td>
		</c:if>
	</tr>
</table>
