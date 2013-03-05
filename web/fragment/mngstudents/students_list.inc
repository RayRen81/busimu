<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="bf" uri="/busimu-functions" %>
<c:choose>
	<c:when test="${selectedCampaign == null}">
		<s:text name="please select campaign from left"/>
	</c:when>
	<c:when test="${selectedCampaign != null && market != null}">
		<c:forEach var="corp" items="${market.corporations}">
			<c:forEach var="student" items="${corp.team.users}">
				<p><a href="<c:url value="/user/studentManagement">
								<c:param name="campaignId" value="${param.campaignId}"/>
								<c:param name="marketId" value="${param.marketId }"/>
								<c:param name="studentId" value="${student.id }"/>
							</c:url> ">${student.name }</a></p>
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:when test="${selectedCampaign != null}">
		<c:forEach var="student" items="${selectedCampaign.users}">
			<p><a href="<c:url value="/user/studentManagement">
							<c:param name="campaignId" value="${param.campaignId}"/>
							<c:param name="studentId" value="${student.id }"/>
						</c:url> ">${student.name }</a></p>
		</c:forEach>
	</c:when>
</c:choose>