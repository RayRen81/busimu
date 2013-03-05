<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
		<c:when test="${not empty param.roundId}">
				<div><%@ include file="/fragment/player/decision_round.inc"%></div>
		</c:when>
		<c:when test="${not empty param.roundId}">
				<div><%@ include file="/fragment/player/decision_campaign.inc"%></div>
		</c:when>
		<c:otherwise>
			<p><s:text name="course.mng.page.nonselect.campaign"/></p>
		</c:otherwise>
</c:choose>