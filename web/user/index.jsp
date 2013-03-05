<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="bf" uri="/busimu-functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/fragment/common_header.inc" %>
<title><s:text name="user.home.title" /></title>
</head>
<body>

<jsp:include page="/fragment/menu.inc"></jsp:include>

<c:choose>
	<c:when test="${'UNKNOWN' == user.type}">
        <div><s:text name="user.home.hello" />${user.name}</div>
		<s:form action="/user/activate" method="post">
		<s:textfield name="license" label="请输入授权码" required="true" />
        <s:submit value="激活" />
	   </s:form>
	</c:when>
	<c:when test="${'TEACHER' == user.type}">
		<div>欢迎您使用系统, ${user.name }老师</div>
	</c:when>
	<c:when test="${'STUDENT' == user.type}">
		<div>欢迎您使用系统, ${user.name }同学</div>
		<br/>
		<s:if test="#attr.user.joinedCampaigns.size() != 0">
		<div>
			<div>您所加入的课程：</div>
			<c:forEach var="campaign" items="${user.joinedCampaigns}">
				<c:set var="market"  value="${bf:getAssignedMarket(user, campaign)}" />
				<div>
					<a href="
						<c:choose>
							<c:when test="${market == null}">
								<c:url value='/user/courseManagement?campaignId=${campaign.campaignId} ' />
							</c:when>
							<c:otherwise>
								<c:url value='/user/courseManagement?campaignId=${campaign.campaignId}&amp;marketId=${market.id }' />
							</c:otherwise>
						</c:choose>
						">${campaign.name}</a></div>
			</c:forEach>
		</div>
		</s:if>
		<s:else>
        	<s:form action="/user/registerCourse" method="post">
          		<s:textfield name="courseId" key="user.index.student.registerCourse.form.input.courseId" />
          		<s:submit name="submit" key="user.index.student.registerCourse.form.submit"/>
        	</s:form>
        </s:else>
	</c:when>
</c:choose>
</body>
</html>