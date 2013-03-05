<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="bf" uri="/busimu-functions" %>

<c:if test="${not empty param.campaignId }">
	<c:set var="campaign" value="${bf:getCampaignById(param.campaignId) }" scope="session"/>
</c:if>
<c:set var="market" value="${bf:getMarketById(param.marketId) }"/>
<div id="tree-menu">
<ul>
  <c:if test="${'TEACHER' == user.type}">
    <c:set var="campaigns" value="${user.createdCampaigns}"/>
  </c:if>
  <c:if test="${'STUDENT' == user.type}">
    <c:set var="campaigns" value="${user.joinedCampaigns}"/>
  </c:if>
  <c:forEach var="aCampaign" items="${campaigns}">
  	<c:choose>
  		<c:when test="${aCampaign.campaignId == campaign.campaignId}">
  			<c:set var="nodeId" value="open" />
  		</c:when>
  		<c:otherwise>
  			<c:set var="nodeId" value="close" />
  		</c:otherwise>
  	</c:choose>
  	<li id="${nodeId}">
    <a href="<c:url value='/user/courseManagement'>
                <c:param name='campaignId' value='${aCampaign.campaignId}' />
             </c:url>">${aCampaign.name}</a>
    <ul>
      <c:forEach var="aMarket" items="${aCampaign.markets}">
        <li><a href="<c:url value='/user/marketManagement'>
                        <c:param name='campaignId' value='${aCampaign.campaignId}' />
                        <c:param name='marketId' value='${aMarket.id}' />
                     </c:url>">${aMarket.name }</a></li>
      </c:forEach>
    </ul>
  </li>
  </c:forEach>
</ul>
</div>
<div id="unassigned-list">
	<p><s:text name="course.mng.page.unassignedStudentList"/></p>
	<div class="student-list">
	<c:if test="${market != null}">
		<c:set var="campaign" value="${market.campaign}"/>
	</c:if>
		<c:set var="unassignedStudents" value="${bf:getUnassignedUsers(campaign) }" />
	<form action="<c:url value='/user/assignStudent' />" method="post">
		<table>
		<c:forEach var="student" items="${unassignedStudents}">
			<tr>
				<c:if test="${market != null}">
					<c:choose>
						<c:when test="${user.type == 'TEACHER' && (campaign.status == 'PREPARE' || campaign.status == 'OPEN')}">
							<td><input name="studentIds" type="checkbox" value="${student.id }"/></td>
						</c:when>
						<c:when test="${user.type == 'STUDENT' }">
							<c:if test="${user.id == student.id}">
								<input name="studentIds" type="hidden" value="${student.id }" />
							</c:if>
						</c:when>
					</c:choose>
				</c:if>
				<c:choose>
					<c:when test="${user.type == 'TEACHER'}">
						<td><span>${student.name }</span></td>
					</c:when>
					<c:when test="${user.type == 'STUDENT' && user.id != student.id}">
						<td><span style="color: gray">${student.name }</span></td>
					</c:when>
					<c:when test="${user.type == 'STUDENT' && user.id == student.id}">
						<td><span style="font-weight: bold">${student.name }</span></td>
					</c:when>
				</c:choose>
			</tr>
		</c:forEach>
		<s:if test="#attr.unassignedStudents.size() != 0 && #attr.market != null">
			<c:if test="${(user.type == 'TEACHER' && campaign.status == 'PREPARE') || campaign.status == 'OPEN'}">
			<tr>
				<td>
					<select id="operation" name="operation">
						<option value="assign"><s:text name="market.manage.assign.form.input.operation.assign"/></option>
						<option value="delete"><s:text name="market.manage.assign.form.input.operation.delete"/></option>
					</select>
				</td>
				<td>
					<s:select id="corps" name="corpId" listKey="id" listValue="name" list="#attr.market.corporations" theme="simple"></s:select>
				</td>
				<td>
					<input type="hidden" name="campaignId" value="${param.campaignId }" />
					<input type="hidden" name="marketId" value="${param.marketId }" />
					<s:submit name="submit" key="market.manage.assign.form.input.submit" theme="simple"/>
				</td>
			</tr>
			</c:if>
		</s:if>
		</table>
	</form>
	</div>
</div>
