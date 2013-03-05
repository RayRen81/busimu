<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${user.type == 'TEACHER' && campaign.status == 'PREPARE'}">
<br />
<hr  size="1"/>
<br />
<div><s:text name="market.edit.title" /></div>
<s:form action="addCorp" method="post">
	<s:textfield name="corpName" key="market.edit.input.corp.name" />
	<s:hidden name="marketId" value="%{#attr.market.id}"/>
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