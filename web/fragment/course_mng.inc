<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
		<c:when test="${campaign != null}">
				<div><%@ include file="/fragment/course_info.inc"%></div>
				<div><%@ include file="/fragment/add_market_in_course.inc"%></div>
				<div><%@ include file="/fragment/course_schedule.inc"%></div>
		</c:when>
		<c:otherwise>
			<p><s:text name="course.mng.page.nonselect.campaign"/></p>
		</c:otherwise>
</c:choose>