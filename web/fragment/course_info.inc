<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
		<tr>
				<td>
				<table>
						<tr>
								<td><s:text name="course.edit.id.name" />:</td>
								<td>${campaign.campaignId }</td>
								<c:if test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
										<td><s:form method="post" action="deleteCourse">
												<input type="hidden" name="campaignId" value="${campaign.campaignId} ">
												<s:submit name="submit" key="course.delete.input.submit" theme="simple" />
										</s:form></td>
								</c:if>
						</tr>
				</table>
				</td>
		</tr>
		<tr>
				<td>
				<form action="<c:url value='/user/editCourse' />" method="post">
				<table>
						<tr>
								<td><s:fielderror /></td>
						</tr>
						<tr>
								<td><s:label key="course.edit.input.name" theme="simple" />: </td>
								<c:choose>
								<c:when test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
									<td><input name="campaignName" value="${campaign.name }" /></td>
									<td><input type="hidden" name="campaignId" value="${campaign.campaignId} "></td>
									<td><s:submit name="submit" key="course.edit.input.submit" theme="simple" /></td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${campaign.name }" /></td>
								</c:otherwise>
								</c:choose>
						</tr>
				</table>
				</form>
				</td>
		</tr>
		<tr>
			<td>
				<s:text name="course.status.current" />: 
				<c:choose>
					<c:when test="${campaign.status == 'PREPARE'}">
						<s:text name="course.status.prepare.name"/>
					</c:when>
					<c:when test="${campaign.status == 'OPEN'}">
						<s:text name="course.status.open.name"/>
					</c:when>
					<c:when test="${campaign.status == 'ONGOING'}">
						<s:text name="course.status.ongoing.name"/>
					</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td>
				<c:if test="${user.type == 'TEACHER' && (campaign.status == 'PREPARE' || campaign.status == 'OPEN')}">
						<s:form action="changeCourseStatus" theme="simple">
							<table>
							<tr>
								<td><s:text name="course.status.input.options.name" />:</td>
								<c:choose>
										<c:when test="${campaign.status == 'PREPARE'}">
												<td><s:select name="courseStatus" list="#{'OPEN' : getText('course.status.open.name')}" theme="simple" /></td>
										</c:when>
										<c:when test="${campaign.status == 'OPEN'}">
												<td><s:select name="courseStatus" list="#{'PREPARE' : getText('course.status.prepare.name'), 'ONGOING' : getText('course.status.start.name')}" theme="simple" /></td>
										</c:when>
								</c:choose>
								<td><input type="hidden" name="campaignId" value="${campaign.campaignId}"></td>
								<td><s:submit name="submit" key="course.status.input.submit" theme="simple" /></td>
							</tr>
							</table>
						</s:form>
				</c:if>
			</td>
		</tr>
</table>
