<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="bf" uri="/busimu-functions" %>

<c:if test="${not empty param.campaignId }">
	<c:set var="campaign" value="${bf:getCampaignById(param.campaignId) }" scope="session"/>
</c:if>
<c:if test="${not empty param.roundId }">
	<c:set var="round" value="${bf:getRoundById(param.roundId) }" scope="session"/>
</c:if>
<div id="tree-menu">
<ul>
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
  	<c:if test="${aCampaign.status != 'ONGOING' && aCampaign.status != 'FINISH'}">
  		<c:set var="linkStyle" value="gray" />
  	</c:if>
  	<li id="${nodeId}">
    <a href="<c:url value='/user/decisionProcess'>
                <c:param name='campaignId' value='${aCampaign.campaignId}' />
    
             </c:url>"><span class="${linkStyle}">${aCampaign.name}</span></a>
    <c:if test="${linkStyle != 'gray' }">         
    <ul>
      <c:forEach var="round" items="${aCampaign.sortedRoundsAsList}">
        <li><a href="<c:url value='/user/decisionProcess'>
                        <c:param name='campaignId' value='${aCampaign.campaignId}' />
                        <c:param name='roundId' value='${round.id}' />
                     </c:url>"><s:text name="decision.process.menu.round" /> ${round.sequenceNum }</a></li>
      </c:forEach>
    </ul>
    </c:if>
  </li>
  </c:forEach>
</ul>
</div>
<div id="team-members">
	<c:set var="corp" value="${bf:getJoinedCorp(user, campaign)}"  />
	<c:if test="${not empty corp }">
	<p><s:text name="decision.process.members.title"/></p>
	<div class="student-list">
		<c:forEach var="aUser" items="${corp.team.users}">
				<c:choose>
					<c:when test="${user.id != aUser.id}">
						<div><span>${aUser.name }</span></div>
					</c:when>
					<c:when test="${aUser.id == aUser.id}">
						<div><span style="font-weight: bold">${user.name }</span></div>
					</c:when>
				</c:choose>
		</c:forEach>
	</div>
	</c:if>
</div>
