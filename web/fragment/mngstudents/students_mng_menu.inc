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
  <c:forEach var="aCampaign" items="${campaigns}">
  	<c:if test="${aCampaign.status == 'ONGOING' || aCampaign.status == 'FINISH'}">
  	<c:choose>
  		<c:when test="${aCampaign.campaignId == campaign.campaignId}">
  			<c:set var="nodeId" value="open" />
  			<c:set var="selectedCampaign" value="${campaign }" />
  		</c:when>
  		<c:otherwise>
  			<c:set var="nodeId" value="close" />
  		</c:otherwise>
  	</c:choose>
  	<li id="${nodeId}">
    <a href="<c:url value='/user/studentManagement'>
                <c:param name='campaignId' value='${aCampaign.campaignId}' />
             </c:url>">${aCampaign.name}</a>
    <ul>
      <c:forEach var="aMarket" items="${aCampaign.markets}">
        <li><a href="<c:url value='/user/studentManagement'>
                        <c:param name='campaignId' value='${aCampaign.campaignId}' />
                        <c:param name='marketId' value='${aMarket.id}' />
                     </c:url>">${aMarket.name }</a></li>
      </c:forEach>
    </ul>
  	</li>
  	</c:if>
  </c:forEach>
</ul>
</div>