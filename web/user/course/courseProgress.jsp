<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bf" uri="/busimu-functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/fragment/common_header.inc" %>
<title>课程进展</title>
</head>
<body>
<jsp:include page="/fragment/menu.inc"></jsp:include>
<c:if test="${param.campaignId != null}">
	<c:set var="campaign" value="${bf:getCampaignById(param.campaignId)}" scope="session"/>
</c:if>
<c:choose>
	<c:when test="${campaign == null || (campaign.status != 'ONGOING' && campaign.status != 'FINISH' && campaign.status != 'LOCK' )}">
		<div><p>请选择以下处于正在进行或已经结束的课程</p></div>
		<div>
		<ul>
		<c:forEach var="campaign" items="${user.createdCampaigns}">
			<c:if test="${campaign.status == 'ONGOING' || campaign.status == 'FINISH' || campaign.status == 'LOCK' }">
				<li><a href="<c:url value='/user/courseProgress'>
								<c:param name="campaignId" value="${campaign.campaignId }"/>
							 </c:url>" >${campaign.name }</a></li>
			</c:if>
		</c:forEach>
		</ul>
		</div>
	</c:when>
	<c:otherwise>
		<div>
			<p><span>当前课程<b><c:out value="${campaign.name}" /></b>进展</span>
			<span style="display: inline;">选择其它课程:
			<c:forEach var="startedCampaign" items="${user.createdCampaigns}">
			<c:if test="${startedCampaign.campaignId != campaign.campaignId && (startedCampaign.status == 'ONGOING' || startedCampaign.status == 'FINISH' || startedCampaign.status == 'LOCK') }">
				<a href="<c:url value='/user/courseProgress'>
								<c:param name="campaignId" value="${startedCampaign.campaignId }"/>
							 </c:url>" >${startedCampaign.name }</a>&nbsp;
			</c:if>
			</c:forEach>
			</span>
			</p>
		</div>
		<div>
		</div>
	</c:otherwise>
</c:choose>
</body>
</html>