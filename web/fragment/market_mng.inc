<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
		<c:when test="${campaign != null}">
			<div><%@ include file="/fragment/market_info.inc"%></div>
			<div><%@ include file="/fragment/add_corp_in_market.inc"%></div>
			<div><%@ include file="/fragment/market_corp.inc"%></div>
		</c:when>
		<c:otherwise>
			<p><s:text name="course.mng.page.nonselect.campaign"/></p>
		</c:otherwise>
</c:choose>
