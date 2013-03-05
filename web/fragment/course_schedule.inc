<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="bf" uri="/busimu-functions" %>

<div>
	<table>
		<tr>
			<td><label><s:text name="course.schedule.input.scheduleWay.name"/>: </label></td>
			<td><s:select id="select-way" name="scheduleWay" list="#{'endDate' : getText('course.schedule.input.scheduleWay.date'), 'lastTime' : getText('course.schedule.input.scheduleWay.interval')}" theme="simple"/></td>
			<c:if test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">			
			<td>
				<s:form action="addRound" method="post" theme="simple">
					<s:hidden name="campaignId" value="%{#attr.campaign.campaignId}" theme="simple"/>
					<s:submit name="submit" key="course.edit.input.round.add.submit" theme="simple"/>
				</s:form>
			</td>
			<td>
				<s:form action="reduceRound" method="post" theme="simple">
					<s:hidden name="campaignId" value="%{#attr.campaign.campaignId}" theme="simple"/>
					<s:submit name="submit" key="course.edit.input.round.reduce.submit" theme="simple"/>
				</s:form>
			</td>
			</c:if>
		</tr>
	</table>
</div>
<div id="endDateShedule">
	<p><s:text name="course.schedule.input.endDateShedule.name" />: </p>
	<form action="<c:url value='/user/saveSchedule' />" method="post">
		<table>
			<tr><td><s:fielderror theme="simple"><s:param value="'startDate'"/></s:fielderror></td></tr>
			<tr>
				<fmt:formatDate value="${bf:getMetaAsDate(campaign, 'campaign:startTime')}" type="date" pattern="${config.endDatePatternForRound}" var="formatedDate"/>
				<td><label><s:text name="course.schedule.input.round.startTime.lable"/>: &nbsp;</label></td>
				<c:choose>
				<c:when test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
				<td><input class="dateInput" name="startDate" type="text" value="${formatedDate}"/></td>
				</c:when>
				<c:otherwise>
				<td><c:out value="${formatedDate}"/></td>
				</c:otherwise>
				</c:choose>
			</tr>
			<tr><td><s:actionerror theme="simple"/></td></tr>
			<c:forEach var="round" items="${campaign.sortedRoundsAsList}" varStatus="index">
				<c:set var="fieldName" value="endDate${index.count}" />
				<tr><td><s:fielderror theme="simple"><s:param value="#attr.fieldName"/></s:fielderror></td></tr>
				<tr>
					<fmt:formatDate value="${round.endDate}" type="date" pattern="${config.endDatePatternForRound}" var="formatedDate"/>
					<c:choose>
					<c:when test="${index.count == '1'}">
						<td><label><s:text name="course.schedule.input.round.practise.lable"/>: &nbsp;</label></td>
					</c:when>
					<c:otherwise>
						<td><label><s:text name="course.schedule.input.round.lable"/> ${index.count - 1}: &nbsp;</label></td>
					</c:otherwise>
					</c:choose>
					<c:choose>
					<c:when test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
					<td><input class="dateInput" name="${fieldName}" type="text" value="${formatedDate}"/></td>
					</c:when>
					<c:otherwise>
					<td><c:out value="${formatedDate}"/></td>
					</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
			<tr><td><input type="hidden" name="campaignId" value="${campaign.id }" /></td></tr>
			<c:if test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
			<s:component template="submit_reset_template.jsp">
      			<s:param name="submit">
       	 			<s:text name="course.schedule.input.round.save" />
      			</s:param>
      			<s:param name="reset">
        			<s:text name="course.schedule.input.round.cancel" />
      			</s:param>
    		</s:component>
    		</c:if>
		</table>
	</form>
</div>
<div id="lastTimeSchedule" class="hidden">
	<p><s:text name="course.schedule.input.lastTimeSchedule.name" />: </p>
	<form action="<c:url value='/user/saveSchedule' />" method="post">
		<table>
			<tr><td><s:actionerror theme="simple"/></td></tr>
			<tr><td><s:fielderror theme="simple"><s:param value="'startDate'"/></s:fielderror></td></tr>
			<tr>
				<fmt:formatDate value="${bf:getMetaAsDate(campaign, 'campaign:startTime')}" type="date" pattern="${config.endDatePatternForRound}" var="formatedDate"/>
				<td><label><s:text name="course.schedule.input.round.startTime.lable"/>: &nbsp;</label></td>
				<c:choose>
				<c:when test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
				<td><input class="dateInput" name="startDate" type="text" value="${formatedDate}"/></td>
				</c:when>
				<c:otherwise>
				<td><c:out value="${formatedDate}"/></td>
				</c:otherwise>
				</c:choose>
			</tr>
			<c:forEach var="round" items="${campaign.sortedRoundsAsList}" varStatus="index">
				<c:set var="fieldName" value="lastTime${index.count}" />
				<tr><td><s:fielderror theme="simple"><s:param value="#attr.fieldName"/></s:fielderror></td></tr>
				<tr>
					<td><label><s:text name="course.schedule.input.round.lable"/> ${index.count}: &nbsp;</label></td>
					<c:choose>
					<c:when test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
					<td><input name="${fieldName}" type="text" value="${round.lastTime }"/></td>
					</c:when>
					<c:otherwise>
					<td><c:out value="${round.lastTime}"/></td>
					</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
			<tr><td><input type="hidden" name="campaignId" value="${campaign.id }" /></td></tr>
			<c:if test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
			<s:component template="submit_reset_template.jsp">
      			<s:param name="submit">
       	 			<s:text name="course.schedule.input.round.save" />
      			</s:param>
      			<s:param name="reset">
        			<s:text name="course.schedule.input.round.cancel" />
      			</s:param>
    		</s:component>
    		</c:if>
		</table>
	</form>
</div>