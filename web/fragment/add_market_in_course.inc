<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
<br />
<hr  size="1"/>
<br />
<s:form action="addMarket" method="post">
	<s:label key="course.eidt.input.market.title" theme="simple"/>
	<s:textfield name="marketName" key="course.edit.input.market.name" />
	<s:textfield name="numOfCorp" key="course.edit.input.market.numOfCorp" />
	<s:hidden name="campaignId" value="%{#attr.campaign.campaignId}"/>
	<s:component template="submit_reset_template.jsp">
      <s:param name="submit">
        <s:text name="course.edit.input.market.submit" />
      </s:param>
      <s:param name="reset">
        <s:text name="course.edit.input.market.reset" />
      </s:param>
    </s:component>
</s:form>
</c:if>
<br />
<hr  size="1"/>
<br />